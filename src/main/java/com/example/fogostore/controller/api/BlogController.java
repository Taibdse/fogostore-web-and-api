package com.example.fogostore.controller.api;

import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.blog.BasicBlog;
import com.example.fogostore.dto.blog.BlogDto;
import com.example.fogostore.model.Blog;
import com.example.fogostore.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody BlogDto blog){
        ResultBuilder result = blogService.create(blog);
        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody BlogDto blog){
        return ResponseEntity.ok(blogService.update(blog));
    }

    @PutMapping("sortIndexes")
    public ResponseEntity update(@RequestBody List<BlogDto> blogs){
        return ResponseEntity.ok(blogService.saveSortIndexes(blogs));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.ok(blogService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity getBySlug(@PathVariable String slug){
        return ResponseEntity.ok(blogService.getBySlug(slug));
    }

    @GetMapping("admin-search")
    public ResponseEntity searchByAdmin(@RequestParam String blog, @RequestParam int page, @RequestParam int size){
        Page<BasicBlog> blogPage = blogService.search(blog, page, size);
        return ResponseEntity.ok(blogPage);
    }

    @DeleteMapping("")
    public ResponseEntity delete(@RequestParam  List<Integer> ids){
        return ResponseEntity.ok(blogService.deleteByIds(ids));
    }


}
