package com.example.spa_case.controller.RESTController;

import com.example.spa_case.service.comboService.ComboService;
import com.example.spa_case.service.comboService.request.ComboSaveRequest;
import com.example.spa_case.service.comboService.response.ComboEditResponse;
import com.example.spa_case.service.comboService.response.ComboListResponse;
import com.example.spa_case.service.comboService.response.ComboListsResponse;
import com.example.spa_case.service.productService.request.ProductEditRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/combos")
@AllArgsConstructor
public class ComboRestController {
    private final ComboService comboService;
    @GetMapping("/list")
    public ResponseEntity<List<ComboListResponse>> getComboList() {
        List<ComboListResponse> comboList = comboService.getComboList();
        return ResponseEntity.ok(comboList);
    }
    @PostMapping
    public void create(@RequestBody ComboSaveRequest request){
        comboService.create(request);
    }
    @GetMapping
    public ResponseEntity<Page<ComboListsResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                         @RequestParam(defaultValue = "") String search,
                                                         @RequestParam(defaultValue = "1") BigDecimal min,
                                                         @RequestParam(defaultValue = "500000000000000000") BigDecimal max)
    {
        return new ResponseEntity<>(comboService.getAll(search,pageable,min,max), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ComboEditResponse> findById(@PathVariable Long id){
        return new  ResponseEntity<>(comboService.findByEdit(id),HttpStatus.OK)  ;
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid ComboSaveRequest request, @PathVariable Long id) throws Exception {
        comboService.update(request,id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        comboService.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
