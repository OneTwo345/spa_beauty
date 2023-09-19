package com.example.spa_case.service.customerService.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CustomerSaveRequest {
    private String name;

    private String email;

    private String phone;

}
