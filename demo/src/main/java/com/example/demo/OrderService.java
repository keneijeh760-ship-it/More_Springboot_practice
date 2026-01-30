package com.example.demo;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Order saveOrder (Order order, Long customerId){

        Customer customer= customerRepository.findById(customerId)
                .orElseThrow(()-> new IllegalStateException("Customer does not exist"));

        order.setCustomer(customer);

        if (order.getTotalPrice().compareTo(new BigDecimal(10)) < 0){
            throw new IllegalArgumentException("Not enough money to place an order");
        } else if (order.getTotalPrice().compareTo(BigDecimal.valueOf(1000)) > 0) {
            throw new IllegalArgumentException("Order Price too High");

            
        }

        if (!"PENDING".equals(order.getStatus()) ){
            throw new IllegalArgumentException("Order Status Not PENDING");

        }

        if ("ADMIN".equals(order.getCustomer().getName())){
            throw new IllegalArgumentException("Admins cannot place orders");
        }
        return orderRepository.save(order);
    }

    public List<Order> findByStatus(String Status){
        return orderRepository.findbyStatus(Status);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }


    @Transactional
    public Order UpdateOrder ( String Status, Long Id){
        Order order = orderRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Order Not Found"));

        if (order.getStatus().equals("DELIVERED")){
            throw new IllegalArgumentException("Order Status DELIVERED cant be changed");

        }

        order.setStatus(Status);
        orderRepository.save(order);

        return order;
    }













}
