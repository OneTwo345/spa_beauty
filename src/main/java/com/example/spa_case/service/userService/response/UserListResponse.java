package com.example.spa_case.service.userService.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserListResponse {
    private Long id;

    private String name;

    private String email;


    private String phone;

    private LocalDate dob;

    private String statusCustomer;
}
