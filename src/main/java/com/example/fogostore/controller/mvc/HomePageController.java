package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.constants.RoutePaths;
import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.common.utils.tree.Node;
import com.example.fogostore.common.utils.tree.TreeUtils;
import com.example.fogostore.dto.blog.BlogDto;
import com.example.fogostore.dto.product.ProductDto;
import com.example.fogostore.model.Category;
import com.example.fogostore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    @RequestMapping(value = {RoutePaths.INDEX, RoutePaths.HOME})
    public String homeAction(Model model){
        List<ProductDto> hotPorducts = productService.getHotProducts();
        List<BlogDto> hotBlogs = blogService.getHotBlogs();
        List<Category> categories = categoryService.getAllActive();
        categories = categories.stream().filter(category -> category.getParentId() == null).collect(Collectors.toList());

        sharedMvcService.addSharedModelAttributes(model, PageType.HOME);

        Map<Integer, Node> categoryMap = (Map<Integer, Node>) model.getAttribute("categoryMap");

        LinkedHashMap<String, List<ProductDto>> hotPorductsMap = new LinkedHashMap<>();

        for (Map.Entry<Integer, Node> entry : categoryMap.entrySet()) {
            Category category = (Category) entry.getValue().getValue();
            List<Integer> categoryIds = TreeUtils.getChidrenListIdFromNode(entry.getValue());
            categoryIds.add(category.getId());

            List<ProductDto> products = hotPorducts.stream().filter(p -> {
                return p.getCategoryIds().stream().filter(id -> categoryIds.contains(id)).count() > 0;
            }).collect(Collectors.toList());
            hotPorductsMap.put(category.getName(), products);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("hotBlogs", hotBlogs);
        model.addAttribute("hotPorductsMap", hotPorductsMap);

        return "user/pages/index";
    }
}
