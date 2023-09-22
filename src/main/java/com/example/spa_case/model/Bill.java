package com.example.spa_case.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table (name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private Long customerQuantity;

    private LocalDateTime timeBook;
    @Column(name = "appointment_time")
    private LocalDateTime appointmentTime;

    private BigDecimal price;

    @OneToMany(mappedBy = "bill")
    private List<BillProduct> billProducts;

    @OneToMany(mappedBy = "bill")
    private List<BillCombo> billCombos;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private User user;





}
