package com.example.fogostore.service;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.common.utils.FileUtils;
import com.example.fogostore.common.utils.tree.Node;
import com.example.fogostore.common.utils.tree.TreeUtils;
import com.example.fogostore.dto.BrandDto;
import com.example.fogostore.dto.CategoryDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.model.Brand;
import com.example.fogostore.model.Category;
import com.example.fogostore.model.PageMetadata;
import com.example.fogostore.repository.CategoryRepository;
import com.example.fogostore.repository.PageMetadataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

public interface CategoryService {
    ResultBuilder create(CategoryDto categoryDto);
    ResultBuilder update(CategoryDto categoryDto);
    ResultBuilder delete(List<Integer> ids);
    CategoryDto getById(int id);
    CategoryDto getBySlug(String slug);
    List<Category> getAllActive();
    List<Integer> getChildIds(int id);
    Map<Integer, Node> getActiveTree();
    ResultBuilder saveSortIndexes(List<CategoryDto> categoryDtos);
}

@Service
class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PageMetadataRepository pageMetadataRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileUtils fileUtils;

    private final String NOTFOUND = "Không tìm thấy danh mục này!";
    private final String CAN_NOT_CREATE_CHILD = "Không thể tạo danh mục con!";
    private final String CAN_NOT_FIND_PARENT = "Không thể tìm thấy danh mục cha!";
    private final String CAN_NOT_SET_PARENT = "Danh mục cha không hợp lệ!";

    @Override
    public ResultBuilder saveSortIndexes(List<CategoryDto> categoryDtos) {
        List<Integer> idList = categoryDtos.stream().map(c -> c.getId()).collect(Collectors.toList());
        List<Category> categoryList = categoryRepository.findByIds(idList);
        Map<Integer, Integer> mapIdSortIndex = new HashMap<>();
        for (CategoryDto categoryDto : categoryDtos) {
            mapIdSortIndex.put(categoryDto.getId(), categoryDto.getSortIndex());
        }
        for (Category category : categoryList) {
            category.setSortIndex(mapIdSortIndex.get(category.getId()));
        }
        categoryRepository.saveAll(categoryList);
        return  ResultBuilder.build().success(true).data(categoryList);
    }

    public HashMap<String, String> validate(CategoryDto categoryDto, boolean editMode){
        HashMap<String, String> errors = new HashMap<>();

        if(editMode){
            if(categoryDto.getId() == null){
                errors.put("NOTFOUND", NOTFOUND);
            } else {
                boolean existed = categoryRepository.existsById(categoryDto.getId());
                if(!existed){
                    errors.put("NOTFOUND", NOTFOUND);
                }
            }
            if(errors.size() > 0) return errors;
        }

        HashMap<String, String> imageErrors =  fileUtils.checkImage(categoryDto.getImage(), editMode);
        if(imageErrors.size() > 0) return imageErrors;


        Integer parentId = categoryDto.getParentId();
        if(parentId != null){
            Category parent = categoryRepository.findById(parentId).orElse(null);
            if(parent != null){
                if(parentId == categoryDto.getId()){
                    errors.put("CAN_NOT_SET_PARENT", CAN_NOT_SET_PARENT);
                } else if(parent.getParentId() != null) {
                    Category grandParent = categoryRepository.findById(parent.getParentId()).orElse(null);
                    if(grandParent.getParentId() != null){
                        boolean existed = categoryRepository.existsById(grandParent.getParentId());
                        if(existed){
                            errors.put("CAN_NOT_CREATE_CHILD", CAN_NOT_CREATE_CHILD);
                        }
                    }
                }
            } else {
                errors.put("CAN_NOT_FIND_PARENT", CAN_NOT_FIND_PARENT);
            }
        }
        return errors;
    }

    @Override
    public ResultBuilder create(CategoryDto categoryDto) {
        ResultBuilder result = new ResultBuilder();
        HashMap<String, String> errors = validate(categoryDto, false);
        if(errors.size() > 0){
            return result.success(false).errors(errors);
        }

        String slug = CustomStringUtils.genSlug(categoryDto.getName());
        Category category = modelMapper.map(categoryDto, Category.class);

        Category found = categoryRepository.findBySlug(slug);
        if(found != null){
            slug = slug + '-' + new Date().getTime();
        }

        category.setSlug(slug);

        HashMap<String, String> value = fileUtils.saveImage(categoryDto.getImage(), slug);
        category.setImage(value.get("fileName"));

        category = categoryRepository.save(category);

        //save page meta data
        PageMetadata pageMetadata = categoryDto.getPageMetadata();
        pageMetadata.setCategoryId(category.getId());
        pageMetadata.setPageType(PageType.SEARCH_PRODUCT_CATEGORY);
        pageMetadataRepository.save(pageMetadata);

        return result.success(true).data(category);
    }

    @Override
    public ResultBuilder update(CategoryDto categoryDto) {
        ResultBuilder result = new ResultBuilder();
        result.setSuccess(false);
        result.setErrors(validate(categoryDto, true));
        if(result.getErrors().size() > 0) return result;


        Category category = categoryRepository.findById(categoryDto.getId()).orElse(null);
        Category newCategory = modelMapper.map(categoryDto, Category.class);


        String slug = CustomStringUtils.genSlug(categoryDto.getName());
        Category found = categoryRepository.findBySlug(slug);
        if(found != null && found.getId() != categoryDto.getId()){
            slug = slug + '-' + new Date().getTime();
        }
        newCategory.setSlug(slug);

        String newImage = categoryDto.getImage();
        String oldImage = category.getImage();
        if(fileUtils.isImageUrl(newImage) || StringUtils.isEmpty(newImage)){
            newCategory.setImage(oldImage);
        } else {
            HashMap<String, String> value = fileUtils.saveImage(newImage, slug);
            fileUtils.removeFile(oldImage);
            newCategory.setImage(value.get("fileName"));
        }

        categoryRepository.save(newCategory);

        //save page meta data
        PageMetadata pageMetadata = categoryDto.getPageMetadata();
        PageMetadata currentPageMetadata = pageMetadataRepository.findByCategoryId(category.getId());
        if(currentPageMetadata != null){
            pageMetadata.setId(currentPageMetadata.getId());
        }
        pageMetadata.setCategoryId(category.getId());
        pageMetadata.setPageType(PageType.SEARCH_PRODUCT_CATEGORY);
        pageMetadataRepository.save(pageMetadata);

        return result.success(true).data(newCategory);
    }

    @Override
    public ResultBuilder delete(@RequestBody List<Integer> ids) {
        categoryRepository.inactiveByIds(ids);
        return ResultBuilder.build().success(true).data(ids);
    }

    @Override
    public CategoryDto getById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return toDto(category);
    }

    CategoryDto toDto(Category category){
        if(category == null) return null;
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        categoryDto.setPageMetadata(pageMetadataRepository.findByCategoryId(category.getId()));
        return categoryDto;
    }

    @Override
    public CategoryDto getBySlug(String slug) {
        return toDto(categoryRepository.findBySlug(slug));
    }

    @Override
    public List<Category> getAllActive() {
        return categoryRepository.findAllActive();
    }

    @Override
    public List<Integer> getChildIds(int id) {
        return categoryRepository.findActiveChildIds(id).stream().map(item -> item.getId()).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, Node> getActiveTree() {
        List<Category> categories = categoryRepository.findAllActive();
        Map<Integer, Node> categoryTree = TreeUtils.convertCategoryListToTree(categories);
        for (Category category : categories) {
            if(category.getParentId() != null){
                categoryTree.remove(category.getId());
            }
        }
        return categoryTree;
    }


}
