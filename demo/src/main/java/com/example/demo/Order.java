package com.example.demo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Order {

    @Id
    @SequenceGenerator(
            name = "order",
            sequenceName = "order",
            allocationSize = 1
    )
    private Long Id;

    @Column(name = "Name",
    nullable = false)
    private String customerName;
    @Column(name = "Price",
    nullable = false)
    private BigDecimal totalPrice;
    @Column(name = "Status",
    nullable = false)
    private String Status;
    @Column(name = "OrderDate",
    nullable = false)
    private LocalDateTime orderDate;

    @Transient
    private Boolean isHighValue(){
      return totalPrice.compareTo(BigDecimal.valueOf(100)) > 100;
    };


    public Long getId() {
        return Id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }




}
