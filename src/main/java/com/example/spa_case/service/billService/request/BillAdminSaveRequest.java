package com.example.spa_case.service.billService.request;

import com.example.spa_case.model.User;
import com.example.spa_case.service.request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BillAdminSaveRequest {
    private String customerName;

    private String customerPhone;

    private String customerQuantity;

//    private String timeBook;

    private String appointmentTime;

    private String productPrice;

    private String comboPrice;

    private List<String> idProduct;

    private List<String> idCombo;

    private SelectOptionRequest user;

    private String price;


}
