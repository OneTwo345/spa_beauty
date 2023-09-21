package com.example.spa_case.service.productService;
//import com.example.spa_case.domain.Service;

import com.example.spa_case.exception.ResourceNotFoundException;
import com.example.spa_case.model.File;
import com.example.spa_case.model.User;
import com.example.spa_case.model.enums.EStatusCustomer;
import com.example.spa_case.service.dto.request.SelectOptionRequest;

import com.example.spa_case.model.Customer;
import com.example.spa_case.model.Product;
import com.example.spa_case.repository.FileRepository;
import com.example.spa_case.repository.ProductRepository;
import com.example.spa_case.service.productService.request.ProductEditRequest;
import com.example.spa_case.service.productService.request.ProductSaveRequest;
import com.example.spa_case.service.productService.response.ProductEditResponse;
import com.example.spa_case.service.productService.response.ProductListResponse;
import com.example.spa_case.service.response.SelectOptionResponse;
import com.example.spa_case.util.AppMessage;
import com.example.spa_case.util.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final FileRepository fileRepository;

    public Page<ProductListResponse> getAll(String search, Pageable pageable, BigDecimal min, BigDecimal max) {
        search = "%" + search + "%";
        return productRepository.searchAllByService(search, pageable, min, max)
                .map(product -> ProductListResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .poster(String.valueOf(product.getPoster().getFileUrl()))
                        .images(String.valueOf(product.getImages()))
                        // Chuyển thành chuỗi
                        .build());
    }

    public void create(ProductSaveRequest request) {
        var product = AppUtil.mapper.map(request, Product.class);
        productRepository.save(product);
        var images = fileRepository.findAllById(request.getImages().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var image : images) {
            image.setProduct(product);
        }
        fileRepository.saveAll(images);
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
                        // Chuyển thành chuỗi
                        .build())
                .collect(Collectors.toList());
    }

    public Product findById(Long id) {
        //de tai su dung
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "Product", id)));
    }

    public ProductEditResponse findByIdProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(AppMessage.ID_NOT_FOUND, "User", id)));
        var result = AppUtil.mapper.map(product, ProductEditResponse.class);
        result.setPoster(product.getPoster().getFileUrl());
        List<String> images = product.getImages()
                .stream()
                .map(File::getFileUrl)
                .collect(Collectors.toList());
        result.setImages(images);
        return result;
    }

    @Transactional
    public void deleteById(Long id) {
        Product product = findById(id);
        fileRepository.deleteAllByProductId(id);
        productRepository.delete(product);
    }

    @Transactional
    public void update(ProductEditRequest request, Long id) throws Exception {
        var productDB = findById(id);

        var images = fileRepository.findAllById(request.getImages().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        List<File> updatedImages = new ArrayList<>(); // Tạo một List tạm thời

        if (images.size() == 0) {
            updatedImages.addAll(productDB.getImages());
        } else {
            updatedImages.addAll(images);
        }

        for (var image : updatedImages) {
            image.setProduct(productDB);
        }

        fileRepository.saveAll(updatedImages);
        productDB.setImages(new ArrayList<>());
        AppUtil.mapper.map(request, productDB);
        productRepository.save(productDB);
    }
    public List<SelectOptionResponse> findAll() {
        return productRepository.findAll()
                .stream().map(product -> new SelectOptionResponse(product.getId()
                        .toString(), product.getName())).collect(Collectors.toList());
    }
}
