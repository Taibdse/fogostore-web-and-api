package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.constants.RoutePaths;
import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.model.Policy;
import com.example.fogostore.service.PolicyService;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PolicyPageController {

    @Autowired
    SharedMvcService sharedMvcService;

    @Autowired
    PolicyService policyService;

    @RequestMapping(value = {RoutePaths.POLICY + "/{slug}",})
    public String policyDetailsPageAction(Model model, @PathVariable String slug){
        Policy policy = policyService.getBySlug(slug);
        model.addAttribute("policy", policy);
        sharedMvcService.addSharedModelAttributes(model, PageType.POLICY);
        return "user/pages/policy-details";
    }
}
