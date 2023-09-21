package com.example.spa_case.controller.RESTController;

import com.example.spa_case.model.Combo;
import com.example.spa_case.service.comboService.ComboListResponse;
import com.example.spa_case.service.comboService.ComboService;
import com.example.spa_case.service.customerService.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/combos")
public class ComboRestController {
    private final ComboService comboService;

    @GetMapping
    public ResponseEntity<List<ComboListResponse>> getComboList() {
        List<ComboListResponse> comboList = comboService.getComboList();
        return ResponseEntity.ok(comboList);
    }


}
