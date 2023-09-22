package com.example.spa_case.service.comboService.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ComboListResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<String> products;
}
