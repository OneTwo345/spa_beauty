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
@Table(name = "combos")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    private File poster;


    @OneToMany(mappedBy = "combo")
    private List<ComboProduct> comboProducts;

    @OneToMany(mappedBy = "combo")
    private List<BillCombo> billCombos;

    @OneToMany(mappedBy = "combo")
    private List<File> images;

    public Combo(Long id) {
        this.id =id;
    }
}
