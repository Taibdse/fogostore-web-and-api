package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.model.Blog;
import com.example.fogostore.model.Policy;
import com.example.fogostore.service.BlogService;
import com.example.fogostore.service.PolicyService;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PolicyPageController {

    @Autowired
    SharedMvcService sharedMvcService;

    @Autowired
    PolicyService policyService;



    @RequestMapping(value = {"/chinh-sach/{slug}",})
    public String policyDetailsPageAction(Model model, @PathVariable String slug){
        Policy policy = policyService.getBySlug(slug);
        model.addAttribute("policy", policy);
        sharedMvcService.addSharedModelAttributes(model, PageType.POLICY);
        return "user/pages/policy-details";
    }
}
