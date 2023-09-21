package com.example.spa_case.controller.RESTController;


import com.example.spa_case.service.billService.BillService;
import com.example.spa_case.service.billService.dto.BillDetailResponse;
import com.example.spa_case.service.billService.dto.BillListResponse;
import com.example.spa_case.service.billService.dto.BillSaveRequest;
import com.example.spa_case.service.billService.response.BillAdminListResponse;
import com.example.spa_case.service.productService.response.ProductListResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/bills/admin")
@AllArgsConstructor
public class BillAdminRestController {
    private final BillService billService;
    @GetMapping
    public ResponseEntity<Page<BillAdminListResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                            @RequestParam(defaultValue = "") String search,
                                                            @RequestParam(defaultValue = "1") BigDecimal min,
                                                            @RequestParam(defaultValue = "500000000000000000") BigDecimal max
    ) {
        return new ResponseEntity<>(billService.getAll(search,pageable, min,max), HttpStatus.OK);
    }
}
