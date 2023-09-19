package com.example.spa_case.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "billCombo")
public class BillCombo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comboName;

    private BigDecimal price;

    private Long serviceCombo;

    @ManyToOne
    private Bill bill;
    @ManyToOne
    private Combo combo;

    public BillCombo(Bill bill, Combo combo) {
        this.bill = bill;
        this.combo = combo;
    }
}
