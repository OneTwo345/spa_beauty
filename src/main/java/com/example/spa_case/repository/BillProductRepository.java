package com.example.spa_case.repository;

import com.example.spa_case.model.Bill;
import com.example.spa_case.model.BillProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillProductRepository extends JpaRepository<BillProduct,Long> {

    void deleteByBill(Bill bill);
    void deleteAllByProductId(Long id);
    void deleteAllByBillId(Long id);

}
