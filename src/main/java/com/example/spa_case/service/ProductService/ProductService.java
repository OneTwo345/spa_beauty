package com.example.spa_case.service.ProductService;


import com.example.spa_case.model.Product;
import com.example.spa_case.repository.ProductRepository;
import com.example.spa_case.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;



}