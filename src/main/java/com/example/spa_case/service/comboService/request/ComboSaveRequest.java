package com.example.spa_case.service.comboService.request;

import com.example.spa_case.service.dto.request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ComboSaveRequest {
    private String name;

    private String price;
    private List<String> idProducts;
    private SelectOptionRequest poster;

    private List<SelectOptionRequest> images;

}
