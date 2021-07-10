package com.example.fogostore.controller.api;

import com.example.fogostore.form.socialPlugin.SocialPluginForm;
import com.example.fogostore.form.socialPlugin.SocialPluginSearchForm;
import com.example.fogostore.service.SocialPluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/social-plugins")
public class SocialPluginController {

    @Autowired
    SocialPluginService socialPluginService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody SocialPluginForm socialPluginForm) {
        return ResponseEntity.ok(socialPluginService.create(socialPluginForm));
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody SocialPluginForm socialPluginForm) {
        return ResponseEntity.ok(socialPluginService.update(socialPluginForm));
    }

    @PutMapping("sortIndexes")
    public ResponseEntity update(@RequestBody List<SocialPluginForm> socialPlugins) {
        return ResponseEntity.ok(socialPluginService.saveSortIndexes(socialPlugins));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.ok(socialPluginService.getById(id));
    }

    @GetMapping("search")
    public ResponseEntity searchByAdmin(SocialPluginSearchForm socialPluginSearchForm) {
        return ResponseEntity.ok(socialPluginService.search(socialPluginSearchForm));
    }

    @DeleteMapping("delete-many")
    public ResponseEntity delete(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok(socialPluginService.deleteByIds(ids));
    }

}
