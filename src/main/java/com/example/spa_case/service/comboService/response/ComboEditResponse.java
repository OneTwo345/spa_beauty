package com.example.spa_case.service.comboService.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ComboEditResponse {
    private Long id;

    private String name;
    private BigDecimal price;
    private List<Long> productsID;
    private String poster;
    private List<String> images;
}
