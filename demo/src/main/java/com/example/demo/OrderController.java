package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/controller")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(Order order){
        orderService.saveOrder(order);
    }


    @GetMapping
    public List<Order> findByStatus(String Status){
        return orderService.findByStatus(Status);
    }

    @GetMapping(path = "/all")
    public List<Order> findAll(){
        return orderService.findAll();
    }



}
