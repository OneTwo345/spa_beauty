package com.example.spa_case.repository;

import com.example.spa_case.model.ComboProduct;
import com.example.spa_case.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboProductRepository extends JpaRepository<ComboProduct,Long> {
    void deleteAllByComboId(Long id);

}
