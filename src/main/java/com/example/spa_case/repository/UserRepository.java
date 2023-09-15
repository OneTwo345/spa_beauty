package com.example.spa_case.repository;

import com.example.spa_case.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
//    @Query("SELECT u FROM User u " +
//            "WHERE " +
//            "u.name LIKE %:search% OR " +
//            "u.email LIKE %:search% OR " +
//            "u.passWord LIKE %:search% OR " +
//            "u.phone LIKE %:search% ")
////            "u.dob LIKE %:search%")
//    Page<User> findUsersBySearch(String search);
}
