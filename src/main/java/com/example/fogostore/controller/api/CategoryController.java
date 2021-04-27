package com.example.fogostore.controller.api;

import com.example.fogostore.dto.CategoryDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.create(categoryDto));
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.update(categoryDto));
    }

    @PutMapping("sortIndexes")
    public ResponseEntity saveSortIndexes(@RequestBody List<CategoryDto> categoryDtos){
        return ResponseEntity.ok(categoryService.saveSortIndexes(categoryDtos));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity getBySlug(@PathVariable String slug){
        return ResponseEntity.ok(categoryService.getBySlug(slug));
    }

    @GetMapping("active")
    public ResponseEntity getAllActive(){
        return ResponseEntity.ok(categoryService.getAllActive());
    }

    @DeleteMapping("")
    public ResponseEntity delete(@RequestParam List<Integer> ids){
        return ResponseEntity.ok(categoryService.delete(ids));
    }

}
