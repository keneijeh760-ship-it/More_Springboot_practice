package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/controller")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return ResponseEntity.status(201).body(order);
    }


    @GetMapping(path = "status")
    public ResponseEntity<List<Order>> findByStatus(String Status){
        return ResponseEntity.ok(orderService.findByStatus(Status));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Order>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }


    @PutMapping
    public ResponseEntity<Order> updateOrder(@RequestBody Order order,
                                             Long id,
                                             @RequestParam String status){
        return ResponseEntity.ok(orderService.UpdateOrder( status, id));
    }





}
