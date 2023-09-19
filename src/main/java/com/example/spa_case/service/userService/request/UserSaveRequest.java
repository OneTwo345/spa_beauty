package com.example.spa_case.service.userService.request;

import com.example.spa_case.service.dto.request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UserSaveRequest {

    private String name;

    private String email;

    private String passWord;

    private String phone;

    private String  dob;

    private String statusCustomer;
    private SelectOptionRequest avatar;

//    private String eLock;


}
