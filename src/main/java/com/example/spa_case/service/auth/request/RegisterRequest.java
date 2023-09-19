package com.example.spa_case.service.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String phone;

    private String password;

    private String email;

}
