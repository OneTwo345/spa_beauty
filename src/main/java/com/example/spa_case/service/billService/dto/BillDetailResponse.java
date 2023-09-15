package com.example.spa_case.service.billService.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BillDetailResponse {
    private Long id;

    private String name;

    private BigDecimal price;

    private LocalDateTime timeBook;

    private List<Long> productIds;
}
