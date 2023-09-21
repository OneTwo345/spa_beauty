package com.example.spa_case.service.comboService;

import com.example.spa_case.model.Combo;
import com.example.spa_case.model.Customer;
import com.example.spa_case.repository.ComboRepository;
import com.example.spa_case.service.productService.response.ProductListResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComboService {
    private final ComboRepository comboRepository;



    public List<ComboListResponse> getComboList() {
        return comboRepository.findAll()
                .stream()
                .map(service -> ComboListResponse.builder()
                        .id(service.getId())
                        .name(service.getName())
                        .price(service.getPrice())
                        // Chuyển thành chuỗi
                        .build())
                .collect(Collectors.toList());
    }
}
