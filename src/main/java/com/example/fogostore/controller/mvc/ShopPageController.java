package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.constants.PageSize;
import com.example.fogostore.common.constants.ProductSortBy;
import com.example.fogostore.common.constants.RoutePaths;
import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.dto.CategoryDto;
import com.example.fogostore.dto.product.ProductDto;
import com.example.fogostore.form.SearchProductForm;
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

    @RequestMapping(value = {RoutePaths.PRODUCT_CATEGORY + "/{categorySlug}", RoutePaths.PRODUCT_CATEGORY})
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
        sharedMvcService.addSharedModelAttributes(model, PageType.SEARCH_PRODUCT_CATEGORY);
        return "user/pages/category-products";
    }

    @RequestMapping(value = {RoutePaths.PRODUCT + "/{productSlug}"})
    public String productDetailsAction(Model model, @PathVariable String productSlug){
        ProductDto product = productService.getBySlug(productSlug);
        model.addAttribute("product", product);
        sharedMvcService.addSharedModelAttributes(model, PageType.PRODUCT_DETAIL);
        return "user/pages/product-details";
    }

    @RequestMapping(value = {RoutePaths.SEARCH_PRODUCT})
    public String productDetailsAction(Model model, SearchProductForm searchProductForm){

        searchProductForm.setBrandId(0);
        searchProductForm.setCategoryId(0);
        searchProductForm.setSortBy(null);
        Page<ProductDto> productPage = productService.searchProducts(searchProductForm, false);
        model.addAttribute("productPage", productPage);
        sharedMvcService.addSharedModelAttributes(model, PageType.PRODUCTS);
        return "user/pages/search-products";
    }

}
