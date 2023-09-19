package com.example.spa_case.repository;

import com.example.spa_case.model.Customer;
import com.example.spa_case.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select u from User u where u.name LIKE :search OR u.email LIKE :search OR u.phone Like :search   ")
    Page<Customer> searchAllCustomer(@Param("search") String search, Pageable pageable );
}
