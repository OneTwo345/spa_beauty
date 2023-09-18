package com.example.spa_case.service.userService.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserSaveRequest {
    private String name;

    private String email;

    private String passWord;

    private String phone;

    private LocalDateTime dob;

}
