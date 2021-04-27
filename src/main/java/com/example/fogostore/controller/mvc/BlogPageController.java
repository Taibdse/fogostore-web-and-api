package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.dto.BlogDto;
import com.example.fogostore.model.Blog;
import com.example.fogostore.service.BlogService;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogPageController {

    @Autowired
    SharedMvcService sharedMvcService;

    @Autowired
    BlogService blogService;

    @RequestMapping(value = {"/bai-viet",})
    public String blogPageAction(Model model,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "1") int page){
        sharedMvcService.addSharedModelAttributes(model, PageType.BLOGS);
        Page<Blog> blogPage = blogService.search("", page, size);
        model.addAttribute("blogPage", blogPage);
        return "user/pages/blog";
    }

    @RequestMapping(value = {"/bai-viet/{slug}",})
    public String blogDetailsPageAction(Model model, @PathVariable String slug){
        BlogDto blog = blogService.getBySlug(slug);

        model.addAttribute("blog", blog);
        sharedMvcService.addSharedModelAttributes(model, PageType.BLOG_DETAIL);
        return "user/pages/blog-details";
    }
}
