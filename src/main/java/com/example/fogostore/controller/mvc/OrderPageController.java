package com.example.fogostore.controller.mvc;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.dto.order.OrderDto;
import com.example.fogostore.service.OrderService;
import com.example.fogostore.service.SharedMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class OrderPageController {

    @Autowired
    OrderService orderService;

    @Autowired
    SharedMvcService sharedMvcService;

    @RequestMapping(value = {"/don-hang/{id}"})
    public String orderDetailsPageAction(@PathVariable Integer id, Model model){
        OrderDto orderDto = orderService.getById(id);
        model.addAttribute("order", orderDto);

        sharedMvcService.addSharedModelAttributes(model, PageType.ORDER_DETAIL);

        return "user/pages/order-details";
    }
}
