package com.example.spa_case.service.billService.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillAdminEditResponse {
    private Long id;

    private String customerName;

    private String customerPhone;

    private Long customerQuantity;

//    private BigDecimal totalProductPrice;
//
//    private BigDecimal totalComboPrice;
    private BigDecimal price;

    private LocalDateTime appointmentTime;

    private List<Long> idProduct;

    private List<Long> idCombo;
}
