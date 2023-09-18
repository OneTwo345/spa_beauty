package com.example.spa_case.controller.RESTController;

import com.example.spa_case.service.productService.ProductService;
import com.example.spa_case.service.productService.request.ProductSaveRequest;
import com.example.spa_case.service.productService.response.ProductListResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductRestController {
    private final ProductService serviceService;
    @GetMapping
    public ResponseEntity<Page<ProductListResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                          @RequestParam(defaultValue = "") String search,
                                                          @RequestParam(defaultValue = "1") BigDecimal min,
                                                          @RequestParam(defaultValue = "500000000000000000") BigDecimal max
    ) {
        return new ResponseEntity<>(serviceService.getAll(search,pageable, min,max), HttpStatus.OK);
    }
    @PostMapping
    public void create(@RequestBody ProductSaveRequest request) {
        serviceService.create(request);
    }
}