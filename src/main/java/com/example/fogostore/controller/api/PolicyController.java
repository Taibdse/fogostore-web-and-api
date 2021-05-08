package com.example.fogostore.controller.api;

import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.model.Policy;
import com.example.fogostore.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    PolicyService policyService;

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        Policy policy = policyService.getById(id);
        return ResponseEntity.ok(policy);
    }

    @GetMapping("/getActive")
    public ResponseEntity getAllActive() {
        return ResponseEntity.ok(policyService.getAllActive());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity getAllActive(@PathVariable String slug) {
        Policy policy = policyService.getBySlug(slug);
        return ResponseEntity.ok(policy);
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody Policy policy) {
        ResultBuilder result = policyService.create(policy);
        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody Policy policy) {
        ResultBuilder result = policyService.update(policy);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        ResultBuilder result = policyService.deleteById(id);
        return ResponseEntity.ok(result);
    }

}
