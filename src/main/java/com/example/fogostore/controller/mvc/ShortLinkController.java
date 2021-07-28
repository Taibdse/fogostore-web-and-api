package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.constants.RoutePaths;
import com.example.fogostore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShortLinkController {

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

    @Autowired
    PolicyService policyService;

    @RequestMapping(value = {"/p/{id}"})
    public String productAction(@PathVariable Integer id) {
        String slug = productService.getSlugById(id);
        String url = RoutePaths.HOME;
        if (slug != null) {
            url = String.format("%s/%s", RoutePaths.PRODUCT, slug);
        }
        return "redirect:" + url;
    }

    @RequestMapping(value = {"/b/{id}"})
    public String blogAction(@PathVariable Integer id) {
        String slug = blogService.getSlugById(id);
        String url = RoutePaths.HOME;
        if (slug != null) {
            url = String.format("%s/%s", RoutePaths.BLOG, slug);
        }
        return "redirect:" + url;
    }

    @RequestMapping(value = {"/po/{id}"})
    public String policyAction(@PathVariable Integer id) {
        String slug = policyService.getSlugById(id);
        String url = RoutePaths.HOME;
        if (slug != null) {
            url = String.format("%s/%s", RoutePaths.POLICY, slug);
        }
        return "redirect:" + url;
    }
}
