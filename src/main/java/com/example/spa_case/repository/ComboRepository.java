package com.example.spa_case.repository;

import com.example.spa_case.model.Combo;
import com.example.spa_case.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ComboRepository extends JpaRepository<Combo,Long> {
    @Query(value = "SELECT b FROM Combo b" +
            " WHERE (b.price BETWEEN :min AND :max) " +
            "AND  ( b.name LIKE :search  " +
            "OR EXISTS (SELECT 1 FROM ComboProduct a " +
            "WHERE a.combo = b AND a.product.name LIKE :search))")
    Page<Combo> searchAllByService(@Param("search") String search, Pageable pageable , @Param("min") BigDecimal min, @Param("max") BigDecimal max);
//    @Transactional
//    void deleteAllByComboAndId(Long id);
}
