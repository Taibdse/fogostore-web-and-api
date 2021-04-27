package com.example.fogostore.config;

import com.example.fogostore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FirstInitialize {

    @Autowired
    UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData(){
        userService.signUpAdmin();
    }

}
