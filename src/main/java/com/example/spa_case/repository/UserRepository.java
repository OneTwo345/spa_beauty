package com.example.spa_case.repository;

import com.example.spa_case.model.Customer;
import com.example.spa_case.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select u from User u where u.name LIKE :search OR u.email LIKE :search OR u.phone Like :search   ")
    Page<User> searchAllUser(@Param("search") String search, Pageable pageable );
    Optional<User> findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(String name, String email, String phone);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);

}