package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.model.PaymentMethod;
import com.example.fogostore.service.PaymentMethodService;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CheckoutPageController {

    @Autowired
    PaymentMethodService paymentMethodService;

    @Autowired
    SharedMvcService sharedMvcService;

    @RequestMapping(value = {"/dat-hang"})
    public String checkoutPageAction(Model model){
        List<PaymentMethod> paymentMethods = paymentMethodService.getAll();
        model.addAttribute("paymentMethods", paymentMethods);

        sharedMvcService.addSharedModelAttributes(model, PageType.CHECKOUT);

        return "user/pages/checkout";
    }
}
