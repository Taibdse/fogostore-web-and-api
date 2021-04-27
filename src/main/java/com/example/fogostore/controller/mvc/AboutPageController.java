package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutPageController {

    @Autowired
    SharedMvcService sharedMvcService;

    @RequestMapping(value = {"/ve-chung-toi"})
    public String aboutPageAction(Model model){
        sharedMvcService.addSharedModelAttributes(model, PageType.ABOUT_US);
        return "user/pages/about-us";
    }
}
