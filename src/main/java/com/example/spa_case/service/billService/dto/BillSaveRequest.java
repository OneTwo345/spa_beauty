package com.example.spa_case.service.billService.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BillSaveRequest {
    private String customerName;

    private String customerPhone;

    private String customerQuantity;


    private String appointmentTime;

    private String productPrice;

    private String comboPrice;

    private List<String> idProduct;

    private List<String> idCombo;

    private String price;

}
