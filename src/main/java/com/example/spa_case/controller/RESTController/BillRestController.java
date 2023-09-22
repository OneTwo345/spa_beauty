package com.example.spa_case.controller.RESTController;


import com.example.spa_case.model.Bill;
import com.example.spa_case.repository.BillProductRepository;
import com.example.spa_case.repository.BillRepository;
import com.example.spa_case.service.billService.BillService;
import com.example.spa_case.service.billService.dto.BillDetailResponse;
import com.example.spa_case.service.billService.dto.BillListResponse;
import com.example.spa_case.service.billService.dto.BillSaveRequest;
import com.example.spa_case.service.billService.request.BillAdminSaveRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
@AllArgsConstructor
public class BillRestController {

    private final BillService billService;


    @GetMapping
    public ResponseEntity<Page<BillListResponse>> getBills(Pageable pageable) {
        return new ResponseEntity<>(billService.getBills(pageable), HttpStatus.OK);
    }


    @PostMapping
    public void create(@RequestBody BillSaveRequest request) {
        billService.create(request);
    }



    @PutMapping("{id}")
    public ResponseEntity<?> updateRoom(@RequestBody @Valid BillSaveRequest request, @PathVariable Long id) {
        billService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDetailResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(billService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable("id") Long billId) {
        billService.delete(billId);
        return ResponseEntity.noContent().build();
    }
}
