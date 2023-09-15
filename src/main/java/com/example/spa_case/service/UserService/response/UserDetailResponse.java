package com.example.spa_case.service.UserService.response;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDetailResponse {
    private Long id;

    private String name;

    private String email;

    private String passWord;

    private String phone;

    @Column(name = "dob")
    private LocalDateTime dob;
}
