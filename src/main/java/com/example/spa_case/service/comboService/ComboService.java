package com.example.spa_case.service.comboService;

import com.example.spa_case.model.Combo;
import com.example.spa_case.model.ComboProduct;
import com.example.spa_case.model.File;
import com.example.spa_case.model.Product;
import com.example.spa_case.repository.ComboProductRepository;
import com.example.spa_case.repository.ComboRepository;
import com.example.spa_case.repository.FileRepository;
import com.example.spa_case.repository.ProductRepository;
import com.example.spa_case.service.comboService.request.ComboSaveRequest;
import com.example.spa_case.service.comboService.response.ComboEditResponse;
import com.example.spa_case.service.comboService.response.ComboListResponse;
import com.example.spa_case.service.comboService.response.ComboListsResponse;
import com.example.spa_case.service.dto.request.SelectOptionRequest;
import com.example.spa_case.service.productService.response.ProductListResponse;
import com.example.spa_case.util.AppMessage;
import com.example.spa_case.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ComboService {
    private final ComboRepository comboRepository;
    private final FileRepository fileRepository;
    private final ComboProductRepository comboProductRepository;
    private final ProductRepository productRepository;

    public void create(ComboSaveRequest request) {
        var combo = AppUtil.mapper.map(request, Combo.class);

        combo = comboRepository.save(combo);
        var files = fileRepository.findAllById(request.getImages().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var file : files) {
            file.setCombo(combo);
        }
        fileRepository.saveAll(files);

        Combo combo1 = combo;
        comboProductRepository.saveAll(request
                .getIdProducts()
                .stream()
                .map(id -> new ComboProduct(combo1, new Product(Long.valueOf(id))))
                .collect(Collectors.toList()));
    }

    public Page<ComboListsResponse> getAll(String search, Pageable pageable, BigDecimal min, BigDecimal max) {
        search = "%" + search + "%";
        return comboRepository.searchAllByService(search, pageable, min, max)
                .map(combo -> ComboListsResponse.builder()
                        .id(combo.getId())
                        .name(combo.getName())
                        .price(combo.getPrice())
                        .poster(String.valueOf(combo.getPoster().getFileUrl()))
                        .images(String.valueOf(combo.getImages()))
                        .products(combo.getComboProducts()
                                .stream()
                                .map(comboProduct -> comboProduct.getProduct().getName())
                                .collect(Collectors.joining(", ")))
                        // Chuyển thành chuỗi
                        .build());
    }

    public Combo findById(Long id) {
        return comboRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format(AppMessage.ID_NOT_FOUND, "combo", id)));
    }
    public ComboEditResponse findByEdit(Long id){
        var combo = comboRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(AppMessage.ID_NOT_FOUND,"COMBO",id)));
        var result = AppUtil.mapper.map(combo,ComboEditResponse.class);
        result.setPoster(combo.getPoster().getFileUrl());
        result.setImages(combo.getImages().stream()
                .map(File::getFileUrl)
                .collect(Collectors.toList()));
        result.setProductsID(combo.getComboProducts()
                .stream().map(product -> product.getProduct().getId())
                .collect(Collectors.toList()));
        return result;
    }
    @Transactional
    public void update(ComboSaveRequest request, Long id) throws Exception {
        var combo = comboRepository.findById(id).orElse(new Combo());
        var images = fileRepository.findAllById(request.getImages().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        List<File> updatedImages = new ArrayList<>(); // Tạo một List tạm thời

        if (images.size() == 0) {
            updatedImages.addAll(combo.getImages());
        } else {
            updatedImages.addAll(images);
        }

        for (var image : updatedImages) {
            image.setCombo(combo);
        }
        fileRepository.saveAll(updatedImages);
        combo.setImages(new ArrayList<>());
        AppUtil.mapper.map(request, combo);
        comboRepository.save(combo);
        comboProductRepository.deleteAllById(combo.getComboProducts().stream()
                .map(ComboProduct::getId)
                .collect(Collectors.toList()));
        var comboProducts = new ArrayList<ComboProduct>();
        for (String idProduct : request.getIdProducts()) {
            Product product = new Product(Long.valueOf(idProduct));
            comboProducts.add(new ComboProduct(combo, product));
        }
        comboProductRepository.saveAll(comboProducts);
    }

    @Transactional
    public void deleteById(Long id) {
        Combo combo = findById(id);
        fileRepository.deleteAllByComboId(id);
        comboProductRepository.deleteAllByComboId(id);
        comboRepository.delete(combo);
    }
}
