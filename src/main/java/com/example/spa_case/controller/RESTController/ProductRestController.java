package com.example.spa_case.controller.RESTController;

import com.example.spa_case.model.Customer;
import com.example.spa_case.repository.ProductRepository;
import com.example.spa_case.service.productService.ProductService;
import com.example.spa_case.service.productService.request.ProductEditRequest;
import com.example.spa_case.service.productService.request.ProductSaveRequest;
import com.example.spa_case.service.productService.response.ProductEditResponse;
import com.example.spa_case.service.productService.response.ProductListResponse;
import com.example.spa_case.service.response.SelectOptionResponse;
import com.example.spa_case.service.userService.request.UserEditRequest;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductRestController {
    private final ProductService productService;
    private final ProductRepository productRepository;
    @GetMapping
    public ResponseEntity<Page<ProductListResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                          @RequestParam(defaultValue = "") String search,
                                                          @RequestParam(defaultValue = "1") BigDecimal min,
                                                          @RequestParam(defaultValue = "500000000000000000") BigDecimal max
    ) {
        return new ResponseEntity<>(productService.getAll(search,pageable, min,max), HttpStatus.OK);
    }
    @PostMapping
    public void create(@RequestBody ProductSaveRequest request) {
        productService.create(request);

    }


    @GetMapping("/list")
    public ResponseEntity<List<ProductListResponse>> getAllProduct() {
        List<ProductListResponse> productListResponses = productService.getAllNoPage();
        return ResponseEntity.ok(productListResponses);
    }
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long Id) {
        productService.deleteById(Id);
        return ResponseEntity.ok("Product deleted successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductEditResponse> find(@PathVariable Long id){
        return  new ResponseEntity<>(productService.findByIdProduct(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid ProductEditRequest request, @PathVariable Long id) throws Exception {
        productService.update(request,id);
        return ResponseEntity.noContent().build();
    }


}

