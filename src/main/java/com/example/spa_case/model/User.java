package com.example.spa_case.model;

import com.example.spa_case.model.enums.ELock;

import com.example.spa_case.model.enums.ERole;
import com.example.spa_case.model.enums.EStatusCustomer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
@Where(clause = "deleted = 0")
@SQLDelete(sql= "UPDATE users SET `deleted` = 1 WHERE (`id` = ?); ")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String passWord;

    private String phone;

    private LocalDate dob;

    private boolean deleted = false;

    @Enumerated(EnumType.STRING)
    private EStatusCustomer statusCustomer;


    @Enumerated(EnumType.STRING)
    private ELock eLock;
    @ManyToOne
    private File avatar;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills;

    @Enumerated(value = EnumType.STRING)
    private ERole role;

    public User(Long id) {
        this.id = id;
    }


}