package com.example.spa_case.repository;

import com.example.spa_case.model.File;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, String> {
    @Transactional
    void deleteAllByProductId(Long id);
    @Transactional
    void deleteAllByComboId(Long id);
    @Query(value = "SELECT * FROM files WHERE id in :myId", nativeQuery = true)
    List<File> findCuaTao(List<String> myId);
//    File deleteFilesByProductId
}