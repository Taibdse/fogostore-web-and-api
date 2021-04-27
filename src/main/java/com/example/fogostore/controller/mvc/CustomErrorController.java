package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    SharedMvcService sharedMvcService;

    @RequestMapping(value = {"/errors"})
    public String showErrorPageAction(Model model){
        sharedMvcService.addSharedModelAttributes(model, null);
        return "user/pages/error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
