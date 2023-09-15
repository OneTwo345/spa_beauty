package com.example.spa_case.repository;

import com.example.spa_case.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Bill,Long> {

}
