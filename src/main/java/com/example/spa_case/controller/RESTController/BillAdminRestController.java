package com.example.spa_case.controller.RESTController;


import com.example.spa_case.service.billService.BillService;
import com.example.spa_case.service.billService.dto.BillDetailResponse;
import com.example.spa_case.service.billService.dto.BillListResponse;
import com.example.spa_case.service.billService.dto.BillSaveRequest;
import com.example.spa_case.service.billService.request.BillAdminSaveRequest;
import com.example.spa_case.service.billService.response.BillAdminEditResponse;
import com.example.spa_case.service.billService.response.BillAdminListResponse;
import com.example.spa_case.service.comboService.request.ComboSaveRequest;
import com.example.spa_case.service.productService.response.ProductEditResponse;
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
@RequestMapping("/api/bills/ad")
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
    @PostMapping()
    public void createAdmin(@RequestBody BillAdminSaveRequest request) {
        billService.createAdmin(request);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BillAdminEditResponse> find(@PathVariable Long id){
        return  new ResponseEntity<>(billService.findByEdit(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid BillAdminSaveRequest request, @PathVariable Long id) throws Exception {
        billService.update(request,id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        billService.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
