package com.example.fogostore.controller.api;

import com.example.fogostore.dto.BrandDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    BrandService brandService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody BrandDto brandDto){
        return ResponseEntity.ok(brandService.create(brandDto));
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody BrandDto brandDto){
        return ResponseEntity.ok(brandService.update(brandDto));
    }

    @PutMapping("sortIndexes")
    public ResponseEntity updateSortIndexes(@RequestBody List<BrandDto> brandDtos){
        return ResponseEntity.ok(brandService.saveSortIndexs(brandDtos));
    }


    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.ok(brandService.getById(id));
    }

    @GetMapping("{slug}")
    public ResponseEntity getBySlug(@PathVariable String slug){
        return ResponseEntity.ok(brandService.getBySlug(slug));
    }

    @GetMapping("active")
    public ResponseEntity getAllActive(){
        return ResponseEntity.ok(brandService.getAllActive());
    }

    @DeleteMapping("")
    public ResponseEntity delete(@RequestParam List<Integer> ids){
        return ResponseEntity.ok(brandService.delete(ids));
    }
}
