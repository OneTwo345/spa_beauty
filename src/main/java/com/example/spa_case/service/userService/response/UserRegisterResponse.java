package com.example.spa_case.service.userService.response;

import lombok.*;

@Getter
@Setter
@Builder
public class UserRegisterResponse {
    private Long id;

    private String name;

    private String email;

    private String passWord;

    private String phone;
}
