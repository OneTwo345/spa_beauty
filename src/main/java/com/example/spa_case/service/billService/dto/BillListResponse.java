package com.example.spa_case.service.billService.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BillListResponse {
    private Long id;

    private String customerName;

    private String customerPhone;

    private Long customerQuantity;

    private BigDecimal price;

    private LocalDateTime timeBook;

    private List<String> products;

    private List<String> combos;

}
