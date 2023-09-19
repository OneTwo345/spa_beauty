package com.example.spa_case.service.userService.response;

import com.example.spa_case.model.enums.ELock;
import com.example.spa_case.model.enums.EStatusCustomer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserEditResponse {
    private Long id;

    private String name;

    private String email;

    private String passWord;

//    private String oldPassword;

    private String phone;

    private LocalDate dob;
    private EStatusCustomer statusCustomer;
    private ELock eLock;
}
