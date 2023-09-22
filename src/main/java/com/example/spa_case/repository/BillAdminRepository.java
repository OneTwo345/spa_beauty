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
//    @Query(value = "SELECT b FROM Bill b" +
//            " WHERE (b.price BETWEEN :min AND :max) " +
//            "AND  ( b.user.name LIKE :search ) " )
//
//    Page<Bill> searchAllByService(@Param("search") String search, Pageable pageable , @Param("min") BigDecimal min, @Param("max") BigDecimal max);
    Page<Bill> findByCustomerNameContainingIgnoreCaseAndPriceBetween(String customerName, BigDecimal price, BigDecimal price2, Pageable pageable);
}
