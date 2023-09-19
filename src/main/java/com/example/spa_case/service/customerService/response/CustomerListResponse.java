package com.example.spa_case.service.customerService.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CustomerListResponse {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String statusCustomer;
}