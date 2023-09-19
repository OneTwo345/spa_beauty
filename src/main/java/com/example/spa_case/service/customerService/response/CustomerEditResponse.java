package com.example.spa_case.service.customerService.response;

import com.example.spa_case.model.enums.EStatusCustomer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CustomerEditResponse {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private LocalDate dob;
    private EStatusCustomer statusCustomer;
}
