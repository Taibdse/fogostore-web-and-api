package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.constants.PageSize;
import com.example.fogostore.common.constants.RoutePaths;
import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.dto.blog.BasicBlog;
import com.example.fogostore.dto.blog.BlogDto;
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

    @RequestMapping(value = {RoutePaths.BLOG})
    public String blogPageAction(Model model,
                                 @RequestParam(defaultValue = PageSize.DEFAULT_PAGE_SIZE + "") int size,
                                 @RequestParam(defaultValue = "1") int page) {
        sharedMvcService.addSharedModelAttributes(model, PageType.BLOGS);
        Page<BasicBlog> blogPage = blogService.search("", page, size);
        model.addAttribute("blogPage", blogPage);
        return "user/pages/blogs";
    }

    @RequestMapping(value = {RoutePaths.BLOG + "/{slug}",})
    public String blogDetailsPageAction(Model model, @PathVariable String slug) {
        BlogDto blog = blogService.getBySlug(slug);

        model.addAttribute("blog", blog);
        sharedMvcService.addSharedModelAttributes(model, PageType.BLOG_DETAIL);
        return "user/pages/blog-details";
    }
}
