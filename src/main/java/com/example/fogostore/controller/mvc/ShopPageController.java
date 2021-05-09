package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.constants.PageSize;
import com.example.fogostore.common.constants.ProductSortBy;
import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.common.utils.tree.Node;
import com.example.fogostore.dto.CategoryDto;
import com.example.fogostore.dto.ProductDto;
import com.example.fogostore.model.Brand;
import com.example.fogostore.model.Category;
import com.example.fogostore.service.BrandService;
import com.example.fogostore.service.CategoryService;
import com.example.fogostore.service.ProductService;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ShopPageController {

    @Autowired
    ProductService productService;

    @Autowired
    SharedMvcService sharedMvcService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @RequestMapping(value = {"/mua-hang"})
    public String shopPageAction(Model model){
        List<ProductDto> productDtoList = new ArrayList<>();
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10};
        for(int i = 0; i < arr.length; i++){
            ProductDto productDto = new ProductDto();
            productDto.setId(i + 1);
            productDto.setName("Name " + (i + 1));
            productDto.setOldPrice((i + 1) * 90000);
            productDto.setNewPrice((i + 1) * 100000);
            productDtoList.add(productDto);
        }

        List<Category> categories = categoryService.getAllActive();

        model.addAttribute("products", productDtoList);
        model.addAttribute("categories", categories);

        sharedMvcService.addSharedModelAttributes(model, PageType.PRODUCTS);

        return "category-products";
    }

    @RequestMapping(value = {"/danh-muc/{categorySlug}", "/danh-muc"})
    public String searchPageByCategory(Model model,
                                       @PathVariable(required = false) String categorySlug,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = ProductSortBy.DEFAULT) String sortBy){
        CategoryDto category = null;
        if(!StringUtils.isEmpty(categorySlug)){
            category = categoryService.getBySlug(categorySlug);
        }

        Page<ProductDto> productPage = productService.getByCategoryOrBrandSlug(categorySlug, page, PageSize.PRODUCT_PAGE_SIZE, sortBy, "category");

        model.addAttribute("productPage", productPage);
        model.addAttribute("category", category);

//        model.addAttribute("DEFAULT", ProductSortBy.DEFAULT);
//        model.addAttribute("CREATED_AT_ASC", ProductSortBy.CREATED_AT_ASC);
//        model.addAttribute("CREATED_AT_DESC", ProductSortBy.CREATED_AT_DESC);
//        model.addAttribute("PRICE_ASC", ProductSortBy.PRICE_ASC);
//        model.addAttribute("PRICE_DESC", ProductSortBy.PRICE_DESC);

        sharedMvcService.addSharedModelAttributes(model, PageType.SEARCH_PRODUCT_CATEGORY);
        return "user/pages/category-products";
    }

//    @RequestMapping(value = {"/hang-xe/{brandSlug}", "/hang-xe"})
//    public String searchPageByBrand(Model model,
//                                       @PathVariable(required = false) String brandSlug,
//                                       @RequestParam(defaultValue = "1") Integer page,
//                                       @RequestParam(defaultValue = ProductSortBy.DEFAULT) String sortBy){
//        Brand brand = null;
//        if(!StringUtils.isEmpty(brandSlug)){
//            brand = brandService.getBySlug(brandSlug);
//        }
//        Page<ProductDto> productPage = productService.getByCategoryOrBrandSlug(brandSlug, page, PageSize.PRODUCT_PAGE_SIZE, sortBy, "brand");
//
//        model.addAttribute("productPage", productPage);
//        model.addAttribute("brand", brand);
//
//        model.addAttribute("DEFAULT", ProductSortBy.DEFAULT);
//        model.addAttribute("CREATED_AT_ASC", ProductSortBy.CREATED_AT_ASC);
//        model.addAttribute("CREATED_AT_DESC", ProductSortBy.CREATED_AT_DESC);
//        model.addAttribute("PRICE_ASC", ProductSortBy.PRICE_ASC);
//        model.addAttribute("PRICE_DESC", ProductSortBy.PRICE_DESC);
//
//        sharedMvcService.addSharedModelAttributes(model, PageType.PRODUCTS);
//
//        return "user/pages/brand-products";
//    }

    @RequestMapping(value = {"/san-pham/{productSlug}"})
    public String productDetailsAction(Model model, @PathVariable String productSlug){
        ProductDto product = productService.getBySlug(productSlug);
        model.addAttribute("product", product);
        sharedMvcService.addSharedModelAttributes(model, PageType.PRODUCT_DETAIL);
        return "user/pages/product-details";
    }

//    @RequestMapping(value = {"/tim-san-pham"})
//    public String productDetailsAction(Model model,
//                                       @RequestParam(defaultValue = "") String search,
//                                       @RequestParam(defaultValue = "1") int page,
//                                       @RequestParam(defaultValue = "0") Integer categoryId,
//                                       @RequestParam(defaultValue = "0") Integer brandId){
//
//        Page<ProductDto> productPage = productService.searchProducts(search, categoryId, brandId, page, PageSize.PRODUCT_PAGE_SIZE, false);
//
//        Map<Integer, Node> categoryTree = categoryService.getActiveTree();
//        Map<Integer, Node> brandTree = brandService.getActiveTree();
//
//        model.addAttribute("brandTree", brandTree);
//        model.addAttribute("categoryTree", categoryTree);
//        model.addAttribute("productPage", productPage);
//
//        sharedMvcService.addSharedModelAttributes(model, PageType.PRODUCTS);
//        return "user/pages/search-product";
//    }

//    @RequestMapping(value = {"/khuyen-mai"})
//    public String productDetailsAction(Model model,
//                                       @RequestParam(defaultValue = "1") int page){
//
//        Page<ProductDto> productPage = productService.getDiscountProducts(page, PageSize.PRODUCT_PAGE_SIZE);
//        model.addAttribute("productPage", productPage);
//        sharedMvcService.addSharedModelAttributes(model, PageType.DISCOUNT);
//        return "user/pages/discount";
//    }
}
