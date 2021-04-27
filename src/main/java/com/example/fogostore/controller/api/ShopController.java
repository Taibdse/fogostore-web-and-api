package com.example.fogostore.controller.api;

import com.example.fogostore.dto.ShopDto;
import com.example.fogostore.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @GetMapping("/latest")
    public ResponseEntity getLatest(){
        return ResponseEntity.ok(shopService.getLatest());
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody ShopDto shop){
        return ResponseEntity.ok(shopService.update(shop));
    }

}
