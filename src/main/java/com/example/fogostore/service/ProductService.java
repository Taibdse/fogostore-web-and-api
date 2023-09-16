package com.example.fogostore.service;

import com.example.fogostore.common.constants.CacheValue;
import com.example.fogostore.common.constants.ImageConstants;
import com.example.fogostore.common.constants.PageSize;
import com.example.fogostore.common.constants.ProductSortBy;
import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.common.objects.ProductInclusionFieldsBuilder;
import com.example.fogostore.common.utils.FileUtils;
import com.example.fogostore.dto.product.BasicProduct;
import com.example.fogostore.dto.product.ProductDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.form.SearchProductForm;
import com.example.fogostore.model.*;
import com.example.fogostore.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ProductService {
    ResultBuilder create(ProductDto productDto);

    ResultBuilder update(ProductDto productDto);

    ResultBuilder delete(int id);

    ResultBuilder deleteMany(List<Integer> ids);

    ResultBuilder saveSortIndexes(List<ProductDto> productDtos);

    Page<ProductDto> getByAdmin(int page, int size, String searchKeyword, Integer categoryId, Integer brandId);

    ProductDto getBySlug(String slug);

    ProductDto getById(Integer id);

    List<ProductDto> getHotProducts();

    List<ProductDto> getByIdList(List<Integer> ids);

    Page<ProductDto> getByCategoryOrBrandSlug(String slug, int page, int size, String sortBy, String type);

    ProductDto toDto(Product product, ProductInclusionFieldsBuilder productInclusionFieldsBuilder);

    Page<ProductDto> getDiscountProducts(int page, int size);

    Page<ProductDto> searchProducts(SearchProductForm searchProductForm, Boolean forAdmin);

    List<ProductDto> getSuggestedProducts(String keyword);

    List<BasicProduct> getAllBasicProducts();

    HashMap<String, String> validate(ProductDto productDto, Boolean editMode);

    String getSlugById(Integer id);
}

@Service
class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductBrandCategoryRepository productBrandCategoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    ProductTagRepository productTagRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PageMetadataRepository pageMetadataRepository;

    @Autowired
    SharedService sharedService;

    @Autowired
    CacheService cacheService;

    @Autowired
    CacheManager cacheManager;

    private final String PRODUCT_NOTFOUND = "không tìm thấy sản phẩm này!";
    private final String CATEGORY_TYPE = "CATEGORY_TYPE";
    private final String BRAND_TYPE = "BRAND_TYPE";

    @Override
    public String getSlugById(Integer id) {
        return productRepository.findSlugById(id);
    }

    @Override
    public List<BasicProduct> getAllBasicProducts() {
        return productRepository.findAllActive();
    }

    @Override
    public ResultBuilder saveSortIndexes(List<ProductDto> productDtos) {
        List<Integer> idList = productDtos.stream().map(p -> p.getId()).collect(Collectors.toList());
        List<Product> productList = productRepository.findByIdList(idList);
        Map<Integer, Integer> mapIdSortIndex = new HashMap<>();
        for (ProductDto productDto : productDtos) {
            mapIdSortIndex.put(productDto.getId(), productDto.getSortIndex());
        }
        for (Product product : productList) {
            product.setSortIndex(mapIdSortIndex.get(product.getId()));
        }
        productRepository.saveAll(productList);
        return ResultBuilder.build().success(true).data(productDtos);
    }

    @Override
    public ResultBuilder deleteMany(List<Integer> ids) {
        productRepository.inactivateByIds(ids);
        return ResultBuilder.build().success(true).data(ids);
    }

    @Override
    public HashMap<String, String> validate(ProductDto productDto, Boolean editMode) {
        HashMap<String, String> errors = new HashMap<>();
        if (StringUtils.isEmpty(productDto.getName())) {
            errors.put("NAME_REQUIRED", "Vui lòng nhập tên sản phẩm!");
        }
        if (StringUtils.isEmpty(productDto.getSlug())) {
            errors.put("SLUG_REQUIRED", "Vui lòng nhập slug sản phẩm!");
        }

        Product foundBySlug = productRepository.findBySlug(productDto.getSlug());

        if (editMode) {
            if (!productRepository.existsById(productDto.getId())) {
                errors.put("NOT_FOUND", "Không tim thấy sản phẩm này!");
            } else if (foundBySlug != null && !foundBySlug.getId().equals(productDto.getId())) {
                errors.put("SLUG_EXISTED", "Slug này đã tồn tại!");
            }
        } else {
            if (foundBySlug != null) {
                errors.put("SLUG_EXISTED", "Slug này đã tồn tại!");
            }
        }

        return errors;
    }

    @Override
    public ResultBuilder create(ProductDto productDto) {
        ResultBuilder result = ResultBuilder.build();
        HashMap<String, String> errors = validate(productDto, false);
        if (!errors.isEmpty()) {
            return result.success(false).errors(errors);
        }

        Product product = modelMapper.map(productDto, Product.class);

        product.setActive(true);
        product.setDescription(sharedService.formatEditorContent(product.getDescription()));
        product.setTechInfo(sharedService.formatEditorContent(product.getTechInfo()));

        product = productRepository.save(product);

        for (Integer brandId : productDto.getBrandIds()) {
            ProductBrandCategory productBrandCategory = new ProductBrandCategory();
            productBrandCategory.setProductId(product.getId());
            productBrandCategory.setBrandId(brandId);
            productBrandCategory.setCategoryId(0);
            productBrandCategoryRepository.save(productBrandCategory);
        }
        for (Integer categoryId : productDto.getCategoryIds()) {
            ProductBrandCategory productBrandCategory = new ProductBrandCategory();
            productBrandCategory.setProductId(product.getId());
            productBrandCategory.setCategoryId(categoryId);
            productBrandCategory.setBrandId(0);
            productBrandCategoryRepository.save(productBrandCategory);
        }

        List<Image> imageList = new ArrayList();
        String slug = product.getSlug();
        Image mainImage = saveProductImageFile(productDto.getMainImage(), slug, true, 1);
        mainImage.setProductId(product.getId());
        imageList.add(mainImage);
        productDto.setMainImage(mainImage.getUrl());

        for (int i = 0; i < productDto.getSubImages().size(); i++) {
            String base64 = productDto.getSubImages().get(i);
            Image subImage = saveProductImageFile(base64, slug, false, 1 + 2);
            subImage.setProductId(product.getId());
            imageList.add(subImage);
            productDto.getSubImages().set(i, subImage.getUrl());
        }

        imageRepository.saveAll(imageList);

        for (int i = 0; i < productDto.getProductTypes().size(); i++) {
            ProductType productType = productDto.getProductTypes().get(i);
            productType.setProductId(product.getId());
            productType.setSortIndex(i + 1);
            productTypeRepository.save(productType);
        }

//        List<ProductTag> newProductTags = getNewProductTagFromIdList(productDto.getTagIds(), product.getId());
//        productTagRepository.saveAll(newProductTags);

        //save page meta data
        PageMetadata pageMetadata = productDto.getPageMetadata();
        if (pageMetadata == null) pageMetadata = new PageMetadata();
        pageMetadata.setProductId(product.getId());
        pageMetadata.setPageType(PageType.PRODUCT_DETAIL);
        pageMetadataRepository.save(pageMetadata);

        return result.success(true).data(productDto);
    }

    public Image findMainImage(List<Image> images) {
        return images.stream().filter(image -> ImageConstants.PRODUCT_MAIN_IMAGE.equals(image.getType()))
                .findFirst().orElse(null);
    }

    public List<Image> findSubImages(List<Image> images) {
        return images.stream().filter(image -> ImageConstants.PRODUCT_SUB_IMAGE.equals(image.getType()))
                .collect(Collectors.toList());
    }

    public Image saveProductImageFile(String dataUrl, String slug, boolean isMainImg, int index) {
        Image image = new Image();
        String type = isMainImg ? ImageConstants.PRODUCT_MAIN_IMAGE : ImageConstants.PRODUCT_SUB_IMAGE;
        image.setType(type);
        image.setSortIndex(index);
        if (fileUtils.isImageUrl(dataUrl)) {
            image.setUrl(dataUrl);
        } else {
            HashMap<String, String> value = fileUtils.saveImage(dataUrl, slug + "-" + index);
            image.setUrl(value.get("fileName"));
        }
        return image;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheValue.findProductById, key = "#productDto.id")
    })
    public ResultBuilder update(ProductDto productDto) {
        ResultBuilder result = ResultBuilder.build();
        HashMap<String, String> errors = validate(productDto, true);
        if (!errors.isEmpty()) {
            return result.success(false).errors(errors);
        }

        if (!productRepository.existsByIdEquals(productDto.getId())) {
            return result.success(false).errors("NOTFOUND", PRODUCT_NOTFOUND);
        }

        Product product = modelMapper.map(productDto, Product.class);

        product.setDescription(sharedService.formatEditorContent(product.getDescription()));
        product.setTechInfo(sharedService.formatEditorContent(product.getTechInfo()));

        product = productRepository.save(product);

        productBrandCategoryRepository.deleteByProductId(productDto.getId());

        for (Integer brandId : productDto.getBrandIds()) {
            ProductBrandCategory productBrandCategory = new ProductBrandCategory();
            productBrandCategory.setProductId(product.getId());
            productBrandCategory.setBrandId(brandId);
            productBrandCategory.setCategoryId(0);
            productBrandCategoryRepository.save(productBrandCategory);
        }

        for (Integer categoryId : productDto.getCategoryIds()) {
            ProductBrandCategory productBrandCategory = new ProductBrandCategory();
            productBrandCategory.setProductId(product.getId());
            productBrandCategory.setCategoryId(categoryId);
            productBrandCategory.setBrandId(0);
            productBrandCategoryRepository.save(productBrandCategory);
        }

        List<Image> imageList = imageRepository.findByProductId(productDto.getId());
        imageRepository.deleteAll(imageList);

        List<Image> savedImgList = new ArrayList<>();
        String slug = product.getSlug();

        Image mainImage = saveProductImageFile(productDto.getMainImage(), slug, true, 1);
        productDto.setMainImage(mainImage.getUrl());
        savedImgList.add(mainImage);

        for (int i = 0; i < productDto.getSubImages().size(); i++) {
            String dataUrl = productDto.getSubImages().get(i);
            Image subImage = saveProductImageFile(dataUrl, slug, false, i + 2);
            productDto.getSubImages().set(i, subImage.getUrl());
            savedImgList.add(subImage);
        }

        //remove images
        for (Image image : imageList) {
            if (image.getType().equals(ImageConstants.PRODUCT_MAIN_IMAGE)) continue;

            boolean canRemove = true;
            for (Image savedImage : savedImgList) {
                if (savedImage.getUrl().equals(image.getUrl())) {
                    canRemove = false;
                    break;
                }
            }
            if (canRemove) fileUtils.removeFile(image.getUrl());
        }

        for (Image image : savedImgList) {
            image.setProductId(product.getId());
            imageRepository.save(image);
        }


        saveProductTypes(productDto);

        List<ProductTag> existedProductTags = productTagRepository.findAllByProductIdEquals(product.getId());
        List<ProductTag> deletedProductTags = getDeletedProductTags(existedProductTags, productDto.getTagIds());
        productTagRepository.deleteAll(deletedProductTags);
        List<ProductTag> newProductTags = getNewProductTagFromIdList(productDto.getTagIds(), product.getId());
        productTagRepository.saveAll(newProductTags);

        //save page meta data
        PageMetadata pageMetadata = productDto.getPageMetadata();
        PageMetadata currentPageMetadata = pageMetadataRepository.findByProductId(product.getId());
        if (currentPageMetadata != null) {
            pageMetadata.setId(currentPageMetadata.getId());
        }
        pageMetadata.setProductId(product.getId());
        pageMetadata.setPageType(PageType.PRODUCT_DETAIL);
        pageMetadataRepository.save(pageMetadata);

        return result.success(true).data(productDto);
    }

    public void saveProductTypes(ProductDto productDto) {
        List<ProductType> existedProductTypes = productTypeRepository.findByProductId(productDto.getId());
        List<Integer> newIds = productDto.getProductTypes().stream().filter(p -> p.getId() != null).map(p -> p.getId()).collect(Collectors.toList());
        List<ProductType> deletedProductTypes = existedProductTypes.stream().filter(productType -> !newIds.contains(productType.getId())).collect(Collectors.toList());
        productTypeRepository.deleteAll(deletedProductTypes);
        final int[] i = {0};
        List<ProductType> savedProductTypes = productDto.getProductTypes().stream().map(dto -> {
            ProductType productType = modelMapper.map(dto, ProductType.class);
            productType.setProductId(productDto.getId());
            productType.setSortIndex(i[0]++);
            return productType;
        }).collect(Collectors.toList());
        savedProductTypes = productTypeRepository.saveAll(savedProductTypes);
        productDto.setProductTypes(savedProductTypes);
    }

    public List<ProductTag> getDeletedProductTags(List<ProductTag> existedProductTags, List<Integer> newTagIds) {
        return existedProductTags.stream()
                .filter(existedProductTag -> !newTagIds.contains(existedProductTag.getTagId()))
                .collect(Collectors.toList());
    }

    public List<ProductTag> getNewProductTagFromIdList(List<Integer> newTagids, Integer productId) {
        return newTagids.stream().map(newTagid -> new ProductTag(productId, newTagid)).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = CacheValue.findProductById, key = "#id")
    public ResultBuilder delete(int id) {
        ResultBuilder result = new ResultBuilder();
        result.setSuccess(false);
        Product found = productRepository.findById(id).orElse(null);
        if (found == null) {
            return result.success(false).errors("NOTFOUND", PRODUCT_NOTFOUND);
        } else {
            productRepository.inactivateById(id);
        }
        return result.success(true);
    }

    @Override
    public ProductDto getById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        ProductInclusionFieldsBuilder productInclusionFieldsBuilder = ProductInclusionFieldsBuilder.build().includeAll();
        return product == null ? null : toDto(product, productInclusionFieldsBuilder);
    }

    @Override
    public ProductDto getBySlug(String slug) {
        Product product = productRepository.findBySlug(slug);
        return product != null ? getById(product.getId()) : null;
//        ProductInclusionFieldsBuilder productInclusionFieldsBuilder = ProductInclusionFieldsBuilder.build().includeAll();
//        return product == null ? null : toDto(product, productInclusionFieldsBuilder);
    }

    @Override
    public Page<ProductDto> getByAdmin(int page, int size, String searchKeyword, Integer categoryId, Integer brandId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ProductInclusionFieldsBuilder productInclusionFieldsBuilder = ProductInclusionFieldsBuilder.build();
        productInclusionFieldsBuilder.setIncludeBrandAndCategoryList(true);
        Page<Product> productPage = productRepository.findBySearchValue(searchKeyword, pageable);

        Page<ProductDto> productDtoPage = productPage.map(new Function<Product, ProductDto>() {
            @Override
            public ProductDto apply(Product product) {
                return toDto(product, productInclusionFieldsBuilder);
            }
        });
        return productDtoPage;
    }

    @Override
    public List<ProductDto> getHotProducts() {
        List<Product> hotProducts = productRepository.findHotProducts();
        ProductInclusionFieldsBuilder builder = ProductInclusionFieldsBuilder.build().includeBrandAndCategoryList(true);
        return hotProducts.stream().map(product -> toDto(product, builder)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getByIdList(List<Integer> ids) {
        List<Product> products = productRepository.findByIdList(ids);
        ProductInclusionFieldsBuilder productInclusionFieldsBuilder = ProductInclusionFieldsBuilder.build();
        productInclusionFieldsBuilder.setIncludeProductTypeList(true);
        return products.stream().map(product -> toDto(product, productInclusionFieldsBuilder)).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> getByCategoryOrBrandSlug(String slug, int page, int size, String sortBy, String type) {
        Sort sort;

        switch (sortBy) {
            case ProductSortBy.CREATED_AT_ASC:
                sort = Sort.by("id").ascending();
                break;
            case ProductSortBy.CREATED_AT_DESC:
                sort = Sort.by("id").descending();
                break;
            case ProductSortBy.PRICE_ASC:
                sort = Sort.by("newPrice").ascending();
                break;
            case ProductSortBy.PRICE_DESC:
                sort = Sort.by("newPrice").descending();
                break;
            default:
                sort = Sort.by("sortIndex").ascending();
        }

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Product> productPage;
        if ("category".equals(type)) {
            if (!StringUtils.isEmpty(slug)) {
                List<Integer> categoryIds = new ArrayList<>();
                Category category = categoryRepository.findBySlug(slug);

                if (category != null) {
                    categoryIds.add(category.getId());
                    List<Integer> childIds = categoryService.getChildIds(category.getId());
                    categoryIds.addAll(childIds);
                }
                productPage = productRepository.findByCategoryIdList(categoryIds, "", pageable);
            } else {
                productPage = productRepository.findAllActive(pageable);
            }
        } else {
            if (!StringUtils.isEmpty(slug)) {
                List<Integer> brandIds = new ArrayList<>();
                Brand brand = brandRepository.findBySlug(slug);
                if (brand != null) {
                    brandIds.add(brand.getId());
                    List<Integer> childIds = brandService.getChildIds(brand.getId());
                    brandIds.addAll(childIds);
                }
                productPage = productRepository.findByBrandIdList(brandIds, "", pageable);
            } else {
                productPage = productRepository.findAllActive(pageable);
            }
        }
        return productPage.map(new Function<Product, ProductDto>() {
            @Override
            public ProductDto apply(Product product) {
                return toDto(product, null);
            }
        });
    }

    public ProductDto toDto(Product product, ProductInclusionFieldsBuilder productInclusionFieldsBuilder) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        if (productInclusionFieldsBuilder == null) {
            productInclusionFieldsBuilder = ProductInclusionFieldsBuilder.build();
        }

        List<Image> images = imageRepository.findByProductId(product.getId());

        Image mainImage = findMainImage(images);
        String mainImageUrl = mainImage != null ? mainImage.getUrl() : "";
        productDto.setMainImage(mainImageUrl);

        if (productInclusionFieldsBuilder.getIncludeSubImages()) {
            List<String> subImages = findSubImages(images).stream().map(img -> img.getUrl()).collect(Collectors.toList());
            productDto.setSubImages(subImages);
        }

        if (productInclusionFieldsBuilder.getIncludeBrandAndCategoryList()) {
            List<Integer> brandIds = new ArrayList<>();
            List<Integer> categoryIds = new ArrayList<>();

            List<ProductBrandCategory> productBrandCategoryList = productBrandCategoryRepository.findAllByProductIdEquals(product.getId());

            productBrandCategoryList.stream().forEach(p -> {
                if (p.getBrandId() != 0) {
                    brandIds.add(p.getBrandId());
                } else {
                    categoryIds.add(p.getCategoryId());
                }
            });

            productDto.setBrandIds(brandIds);
            productDto.setCategoryIds(categoryIds);

            List<Brand> brands = brandRepository.findByIds(brandIds);
            List<Category> categories = categoryRepository.findByIds(categoryIds);
            productDto.setCategories(categories);
            productDto.setBrands(brands);
        }

        if (productInclusionFieldsBuilder.getIncludeProductTypeList()) {
            List<ProductType> productTypes = productTypeRepository.findByProductId(product.getId());
            productDto.setProductTypes(productTypes);
        }

        if (productInclusionFieldsBuilder.getIncludeTagList()) {
            List<Tag> tags = tagRepository.findAllActiveByProductId(product.getId());
            productDto.setTags(tags);
            productDto.setTagIds(tags.stream().map(t -> t.getId()).collect(Collectors.toList()));
        }

        if (productInclusionFieldsBuilder.getIncludePageMetadata()) {
            productDto.setPageMetadata(pageMetadataRepository.findByProductId(product.getId()));
        }

        if (productInclusionFieldsBuilder.getIncludeRelatedProducts()) {
            String relatedProductIds = product.getRelatedProductIds();
            if (!StringUtils.isEmpty(relatedProductIds)) {
                List<ProductDto> relatedProducts = new ArrayList<>();
                for (String s : relatedProductIds.split(",")) {
                    Integer id = Integer.parseInt(s);
                    Product p = productRepository.findById(id).orElse(null);
                    if (p != null && p.isActive()) relatedProducts.add(toDto(p, null));
                }
                productDto.setRelatedProducts(relatedProducts);
            }
        }

        return productDto;
    }

    @Override
    public Page<ProductDto> getDiscountProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("sortIndex").ascending());
        Page<Product> productPage = productRepository.findDiscountProducts(pageable);
        ProductInclusionFieldsBuilder productInclusionFieldsBuilder = ProductInclusionFieldsBuilder.build();

        return productPage.map(new Function<Product, ProductDto>() {
            @Override
            public ProductDto apply(Product product) {
                return toDto(product, productInclusionFieldsBuilder);
            }
        });
    }

    @Override
    public Page<ProductDto> searchProducts(SearchProductForm searchProductForm, Boolean forAdmin) {
        String searchValue = Optional.ofNullable(searchProductForm.getSearchValue()).orElse("");
        int categoryId = Optional.ofNullable(searchProductForm.getCategoryId()).orElse(0);
        int brandId = Optional.ofNullable(searchProductForm.getBrandId()).orElse(0);
        int page = Optional.ofNullable(searchProductForm.getPage()).orElse(1);
        int size = Optional.ofNullable(searchProductForm.getSize()).orElse(PageSize.PAGE_SIZE_10);
        String sortBy = Optional.ofNullable(searchProductForm.getSortBy()).orElse(ProductSortBy.CREATED_AT_ASC);

        Sort sort = Sort.by("sortIndex").ascending();

        switch (sortBy) {
            case ProductSortBy.CREATED_AT_ASC:
                sort = Sort.by("id").ascending();
                break;
            case ProductSortBy.CREATED_AT_DESC:
                sort = Sort.by("id").descending();
                break;
            case ProductSortBy.SORT_INDEX_DESC:
                sort = Sort.by("sortIndex").descending();
                break;
            case ProductSortBy.SORT_INDEX_ASC:
                sort = Sort.by("sortIndex").ascending();
                break;
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Product> productPage;
        ProductInclusionFieldsBuilder productInclusionFieldsBuilder = ProductInclusionFieldsBuilder.build();
        if (forAdmin) {
            productInclusionFieldsBuilder.setIncludeBrandAndCategoryList(true);
            productInclusionFieldsBuilder.setIncludeProductTypeList(false);
        }

        if (categoryId == 0 && brandId == 0) {
            productPage = productRepository.findBySearchValue(searchValue, pageable);
        } else if (categoryId == 0) {
            List<Integer> brandIds = brandService.getChildIds(brandId);
            brandIds.add(brandId);
            productPage = productRepository.findByBrandIdList(brandIds, searchValue, pageable);
        } else if (brandId == 0) {
            List<Integer> categoryIds = categoryService.getChildIds(categoryId);
            categoryIds.add(categoryId);
            productPage = productRepository.findByCategoryIdList(categoryIds, searchValue, pageable);
        } else {
            List<Integer> categoryIds = categoryService.getChildIds(categoryId);
            categoryIds.add(categoryId);
            List<Integer> brandIds = brandService.getChildIds(brandId);
            brandIds.add(brandId);

            productPage = productRepository.findByEndUser(searchValue, categoryIds, brandIds, pageable);
        }

        return productPage.map(new Function<Product, ProductDto>() {
            @Override
            public ProductDto apply(Product product) {
                return toDto(product, productInclusionFieldsBuilder);
            }
        });
    }

    @Override
    public List<ProductDto> getSuggestedProducts(String keyword) {
        Pageable pageable = PageRequest.of(0, 8, Sort.by("sortIndex").ascending());
        Page<Product> productPage = productRepository.findBySearchValue(keyword, pageable);
        return productPage.getContent().stream().map(p -> toDto(p, null)).collect(Collectors.toList());
    }
}
