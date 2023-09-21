package com.example.spa_case.service.productService;
//import com.example.spa_case.domain.Service;

import com.example.spa_case.model.Customer;
import com.example.spa_case.model.Product;
import com.example.spa_case.repository.ProductRepository;
import com.example.spa_case.service.productService.request.ProductSaveRequest;
import com.example.spa_case.service.productService.response.ProductListResponse;
import com.example.spa_case.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductListResponse> getAll(String search , Pageable pageable, BigDecimal min, BigDecimal max) {
        search = "%"+search+"%";
        return productRepository.searchAllByService(search, pageable,min,max)
                .map(service -> ProductListResponse.builder()
                        .id(service.getId())
                        .name(service.getName())
                        .description(service.getDescription())
                        .price(service.getPrice())
                        .poster(String.valueOf(service.getPoster()))
                        // Chuyển thành chuỗi
                        .build());
    }


    public void create(ProductSaveRequest request) {
        var product = AppUtil.mapper.map(request, Product.class);
        productRepository.save(product); // Sử dụng serviceRepository.save(service) thay vì bookRepository.save(book)

    }


    public List<ProductListResponse> getAllNoPage() {
        return productRepository.findAll()
                .stream()
                .map(service -> ProductListResponse.builder()
                        .id(service.getId())
                        .name(service.getName())
                        .description(service.getDescription())
                        .price(service.getPrice())
                        .poster(service.getPoster().getFileUrl())
//                        .image(service.getImages().toString())toString
                        // Chuyển thành chuỗi
                        .build())
                .collect(Collectors.toList());
    }

}
