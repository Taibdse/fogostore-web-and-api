package com.example.fogostore.controller.api;

import com.example.fogostore.service.SitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sitemap")
public class SitemapController {

    @Autowired
    SitemapService sitemapService;

    @PutMapping("")
    public ResponseEntity update(){
      sitemapService.genSitemap();
      return ResponseEntity.ok("ok");
    }
}
