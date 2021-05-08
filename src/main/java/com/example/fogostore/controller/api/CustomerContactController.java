package com.example.fogostore.controller.api;

import com.example.fogostore.service.CustomerContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/customer-contact")
public class CustomerContactController {

    @Autowired
    CustomerContactService customerContactService;

}
