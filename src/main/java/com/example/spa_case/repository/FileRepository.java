package com.example.spa_case.repository;

import com.example.spa_case.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}