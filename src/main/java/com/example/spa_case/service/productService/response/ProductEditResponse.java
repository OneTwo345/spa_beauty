package com.example.spa_case.service.productService.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductEditResponse {
    private Long id;

    private String name;

    private String productsName;

    private BigDecimal price;

    private String poster;
    private List<String> images;
}
