package com.example.fogostore.controller.api;

import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.WebsiteDto;
import com.example.fogostore.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/website")
public class WebsiteController {

    @Autowired
    WebsiteService websiteService;


    @PutMapping("")
    public ResponseEntity update(@RequestBody WebsiteDto websiteDto){
        ResultBuilder result = websiteService.update(websiteDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/latest")
    public ResponseEntity getTheLatest(){
        WebsiteDto websiteDto = websiteService.getThelatest();
        return ResponseEntity.ok(websiteDto);
    }

}
