package com.example.spa_case.service.billService.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BillAdminListResponse {
    private Long id;

    private String customerName;

    private String customerPhone;
    private String customerEmail;

    private Long customerQuantity;

    private BigDecimal totalProductPrice;

    private BigDecimal totalComboPrice;
    private BigDecimal price;

    private LocalDateTime timeBook;
    private LocalDateTime appointmentTime;
    private String user;
    private String idProduct;

    private String idCombo;

}
