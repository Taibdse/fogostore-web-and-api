package com.example.fogostore.controller.api;

import com.example.fogostore.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    ViewService viewService;

    @GetMapping("/views")
    public ResponseEntity getViewsPerDateInMonth(@RequestParam String time){
        return ResponseEntity.ok(viewService.getViewsPerDayInMonth(time));
    }
}
