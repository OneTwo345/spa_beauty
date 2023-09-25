package com.example.spa_case.repository;

import com.example.spa_case.model.BillCombo;
import com.example.spa_case.model.BillProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillComboRepository extends JpaRepository<BillCombo,Long> {
    void deleteAllByComboId(Long id);
    void deleteAllByBillId(Long id);
}
