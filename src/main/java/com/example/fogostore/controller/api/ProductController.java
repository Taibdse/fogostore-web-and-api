package com.example.fogostore.controller.api;

import com.example.fogostore.dto.product.ProductDto;

import com.example.fogostore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/end-user/products-in-cart")
    public ResponseEntity getAvailableProductsByIds(@RequestParam String ids){
        List<ProductDto> productDtos = new ArrayList<>();
        if(!StringUtils.isEmpty(ids)) {
            String[] arr = ids.split(",");

            List<Integer> idList = Arrays.asList(arr).stream()
                    .map(item -> Integer.parseInt(item))
                    .collect(Collectors.toList());
            productDtos = productService.getByIdList(idList);
        }
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/end-user/suggestions")
    public List<ProductDto> getSuggestedProducts(@RequestParam String keyword){
        return productService.getSuggestedProducts(keyword);
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.create(productDto));
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.update(productDto));
    }

    @PutMapping("sortIndexes")
    public ResponseEntity saveSortIndexes(@RequestBody List<ProductDto> productDtos){
        return ResponseEntity.ok(productService.saveSortIndexes(productDtos));
    }

    @GetMapping("/details-slug/{slug}")
    public ResponseEntity getProductBySlug(@PathVariable String slug){
        return ResponseEntity.ok(productService.getBySlug(slug));
    }

    @GetMapping("/details-id/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return ResponseEntity.ok(productService.delete(id));
    }

    @DeleteMapping("deleteMany")
    public ResponseEntity delete(@RequestParam List<Integer> productIds){
        return ResponseEntity.ok(productService.deleteMany(productIds));
    }

    @GetMapping("admin-search")
    public ResponseEntity getProductsListByAdmin(
            @RequestParam String searchKeyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "0") Integer brandId,
            @RequestParam(defaultValue = "0") Integer categoryId){
        return ResponseEntity.ok(productService.searchProducts(searchKeyword, categoryId, brandId, page, size, true));
    }

    @GetMapping("enduser/search")
    public ResponseEntity getProductsListByUser(
                    @RequestParam String searchKeyword,
            @RequestParam int page,
            @RequestParam int size){
        Integer categoryId = 0;
        Integer brandId = 0;
        return ResponseEntity.ok(productService.searchProducts(searchKeyword, categoryId, brandId, page, size, false));
    }

    @GetMapping("/all-basic")
    public ResponseEntity getProductById(){
        return ResponseEntity.ok(productService.getAllBasicProducts());
    }
}
