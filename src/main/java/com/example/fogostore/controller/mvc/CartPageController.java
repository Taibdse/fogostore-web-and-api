package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartPageController {

    @Autowired
    SharedMvcService sharedMvcService;

    @RequestMapping(value = {"/gio-hang"})
    public String cartPageAction(Model model){
        sharedMvcService.addSharedModelAttributes(model, PageType.CART);
        return "user/pages/cart";
    }
}
