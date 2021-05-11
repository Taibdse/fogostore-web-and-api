package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.dto.BlogDto;
import com.example.fogostore.dto.product.ProductDto;
import com.example.fogostore.model.Category;
import com.example.fogostore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomePageController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SharedMvcService sharedMvcService;

    @Autowired
    WebsiteService websiteService;

    @Autowired
    BlogService blogService;

    @RequestMapping(value = {"/", "/trang-chu"})
    public String homeAction(Model model){
        int size = 10;
        List<ProductDto> hotPorducts = productService.getHotProducts();
        List<BlogDto> hotBlogs = blogService.getHotBlogs();
        List<Category> categories = categoryService.getAllActive();
        categories = categories.stream().filter(category -> category.getParentId() == null).collect(Collectors.toList());

//        List<List<ProductDto>> products = new ArrayList<>();
//        List<ProductDto> tempList = new ArrayList<>();
//        for (int i = 0; i < hotPorducts.size(); i++){
//            if(i % size == 0){
//                if(i > 0) products.add(tempList);
//                tempList = new ArrayList<>();
//            }
//
//            tempList.add(hotPorducts.get(i));
//        }
//        products.add(tempList);


        model.addAttribute("categories", categories);
        model.addAttribute("hotProducts", hotPorducts);
        model.addAttribute("hotBlogs", hotBlogs);

        sharedMvcService.addSharedModelAttributes(model, PageType.HOME);

        return "user/pages/index";
    }
}
