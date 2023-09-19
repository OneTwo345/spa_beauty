package com.example.spa_case.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;
    //new Image set Poster
    // new List Image set images;

    @OneToOne
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
