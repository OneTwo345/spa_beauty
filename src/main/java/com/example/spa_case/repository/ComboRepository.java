package com.example.spa_case.repository;

import com.example.spa_case.model.Combo;
import com.example.spa_case.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboRepository extends JpaRepository<Combo,Long> {

}
