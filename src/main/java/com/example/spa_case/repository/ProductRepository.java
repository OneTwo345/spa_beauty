package com.example.spa_case.repository;

import com.example.spa_case.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT b FROM Product b" +
            " WHERE (b.price BETWEEN :min AND :max) " +
            "AND  ( b.name LIKE :search  " +
            "OR b.description LIKE :search  )" )
    Page<Product> searchAllByService(@Param("search") String search, Pageable pageable , @Param("min") BigDecimal min, @Param("max") BigDecimal max);


}