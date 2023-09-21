package com.example.spa_case.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;


    @ManyToOne
    private File poster;

    @OneToMany(mappedBy = "product")
    private List<File> images;


    @OneToMany(mappedBy = "product")
    private List<ComboProduct> comboProducts;

    @OneToMany(mappedBy = "product")
    private List<BillProduct> billProducts;


    public Product(Long id) {
        this.id = id;
    }


}
