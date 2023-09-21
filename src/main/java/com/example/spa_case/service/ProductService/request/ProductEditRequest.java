package com.example.spa_case.service.productService.request;

import com.example.spa_case.service.dto.request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductEditRequest {
    private String id;
    private String name;

    private String price;

    private String description;

    private SelectOptionRequest poster;

    private List<SelectOptionRequest> images;

}
