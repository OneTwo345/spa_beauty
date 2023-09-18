package com.example.spa_case.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_products")
@Data
@NoArgsConstructor
public class BillProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private BigDecimal price;

    private Long productQuantity;


    @ManyToOne
    private Bill bill;
    @ManyToOne
    private Product product;

    public BillProduct(Bill bill, Product product) {
        this.bill = bill;
        this.product = product;
    }
}
