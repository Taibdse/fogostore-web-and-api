package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.dto.ProductDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.model.Category;
import com.example.fogostore.service.CategoryService;
import com.example.fogostore.service.ProductService;
import com.example.fogostore.service.SharedMvcService;
import com.example.fogostore.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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

    @RequestMapping(value = {"/", "/trang-chu"})
    public String homeAction(Model model){
//        int size = 10;
//        List<ProductDto> hotPorducts = productService.getHotProducts();
//        List<Category> categories = categoryService.getAllActive();
//        categories = categories.stream().filter(category -> category.getParentId() == null).collect(Collectors.toList());
//
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
//
//
//        model.addAttribute("categories", categories);
//        model.addAttribute("hotProducts", products);
//
//        sharedMvcService.addSharedModelAttributes(model, PageType.HOME);
//
        return "user/pages/index";
    }
}
