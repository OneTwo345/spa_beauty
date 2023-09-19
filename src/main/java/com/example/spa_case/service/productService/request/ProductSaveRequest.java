package com.example.spa_case.service.productService.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductSaveRequest {
    private String name;

    private String price;

    private String description;

    private String poster;

    private List<String> image;

}
