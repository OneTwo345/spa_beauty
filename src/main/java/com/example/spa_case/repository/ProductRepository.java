package com.example.spa_case.repository;

import com.example.spa_case.model.Bill;
import com.example.spa_case.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
