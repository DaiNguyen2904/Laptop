package com.soa.manageLaptop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // This should be not nullable
    @JsonBackReference
    private User user;

    @ElementCollection
    @CollectionTable(name = "orders_products", joinColumns = @JoinColumn(name = "orders_id"))
    @Column(name = "product_id")
    private List<Long> productIds = new ArrayList<>();


    public Order() {}
}
