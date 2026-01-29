package com.example.demo;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder (Order order){

        if (order.getTotalPrice().compareTo(new BigDecimal(10)) < 10){
            throw new IllegalArgumentException("Not enough money to place an order");
        } else if (order.getTotalPrice().compareTo(BigDecimal.valueOf(1000)) > 1000) {
            throw new IllegalArgumentException("Order Price too High");

            
        }

        if (order.getStatus() != "PENDING"){
            throw new IllegalArgumentException("Order Status Not PENDING");

        }

        if (order.getCustomerName() == "ADMIN"){
            throw new IllegalArgumentException("Admins cannot place orders");
        }
        orderRepository.save(order);
    }

    public List<Order> findByStatus(String Status){
        return orderRepository.findbyStatus(Status);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }
}
