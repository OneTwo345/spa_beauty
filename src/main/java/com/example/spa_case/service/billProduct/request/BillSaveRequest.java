package com.example.spa_case.service.billProduct.request;

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

    private String timeBook;

    private List<String> idProduct;


}
