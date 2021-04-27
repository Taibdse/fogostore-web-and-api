package com.example.fogostore.controller.api;

import com.example.fogostore.model.Tag;
import com.example.fogostore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody Tag tag){
        return ResponseEntity.ok(tagService.create(tag));
    }

    @PutMapping("")
    public ResponseEntity updateCategory(@RequestBody Tag tag){
        return ResponseEntity.ok(tagService.update(tag));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.ok(tagService.getById(id));
    }

    @GetMapping("active")
    public ResponseEntity searchByAdmin(){
        return ResponseEntity.ok(tagService.getAllActive());
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return ResponseEntity.ok(tagService.delete(id));
    }

}
