package com.example.spa_case.repository;

import com.example.spa_case.model.Bill;
import com.example.spa_case.model.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface BillAdminRepository extends JpaRepository<Bill,Long> {
    @Query(value = "SELECT b FROM Bill b" +
            " WHERE (b.price BETWEEN :min AND :max) " +
            "AND  ( b.customerName LIKE :search OR b.customerEmail Like :search OR b.user.name like :search Or exists (SELECT 1 FROM BillCombo c WHERE c.combo = b AND c.combo.name LIKE :search)" +
            " OR EXISTS (SELECT 1 FROM BillProduct p " +
            "WHERE p.bill = b AND p.product.name LIKE :search))")
    Page<Bill> searchAllByService(@Param("search") String search, Pageable pageable , @Param("min") BigDecimal min, @Param("max") BigDecimal max);
}
