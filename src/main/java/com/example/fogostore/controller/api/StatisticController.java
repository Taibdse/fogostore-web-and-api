package com.example.fogostore.controller.api;

import com.example.fogostore.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @GetMapping("/product")
    public ResponseEntity getProductStatistics() {
        return ResponseEntity.ok(statisticService.getProductStatistics());
    }

    @GetMapping("/all")
    public ResponseEntity getAllStatistics() {
        return ResponseEntity.ok(statisticService.getStatistics());
    }
}
