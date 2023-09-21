package com.example.spa_case.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comboProduct")
public class ComboProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Combo combo;

    @ManyToOne
    private Product product;
    public ComboProduct(Combo combo, Product product){
        this.combo=combo;
        this.product=product;
    }
}
