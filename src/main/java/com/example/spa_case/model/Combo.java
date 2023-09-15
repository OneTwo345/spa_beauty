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
@Table(name = "combos")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    private Image poster;


    @OneToMany(mappedBy = "combo")
    private List<ComboProduct> comboProducts;

    @OneToMany(mappedBy = "combo")
    private List<BillCombo> billCombos;

    @OneToMany(mappedBy = "combo")
    private List<Image> images;
}
