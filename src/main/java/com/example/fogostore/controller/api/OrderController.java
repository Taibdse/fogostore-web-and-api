package com.example.fogostore.controller.api;

import com.example.fogostore.common.constants.OrderSortBy;
import com.example.fogostore.dto.OrderDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/end-user/submit")
    public ResponseEntity submitOrder(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.submitOrder(orderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.update(orderDto));
    }

    @GetMapping("/admin-search")
    public ResponseEntity searchByAdmin(@RequestParam(defaultValue = "") String customer,
                                        @RequestParam("fromDate") Date fromDate,
                                        @RequestParam("toDate") Date toDate,
                                        @RequestParam(defaultValue = "all") String status,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(defaultValue = OrderSortBy.CREATED_AT_DESC) String sortBy){

        Page<OrderDto> orderDtoPage = orderService.searchByAdmin(customer, fromDate, toDate, status, page, size, sortBy);
        return ResponseEntity.ok(orderDtoPage);
    }

}
