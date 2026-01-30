package com.example.demo;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;

import java.util.List;

@Entity
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customer_id",
            sequenceName = "customer_id",
            allocationSize = 1
    )

    @GeneratedValue(
            generator = "customer_id",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
