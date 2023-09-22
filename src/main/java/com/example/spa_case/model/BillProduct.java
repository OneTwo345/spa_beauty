package com.example.spa_case.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_products")
@Getter
@Setter
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
