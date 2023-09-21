package com.example.spa_case.service.billService;

import com.example.spa_case.model.Bill;
import com.example.spa_case.model.BillCombo;
import com.example.spa_case.model.BillProduct;
import com.example.spa_case.model.Product;
import com.example.spa_case.repository.*;
import com.example.spa_case.service.billService.dto.BillDetailResponse;
import com.example.spa_case.service.billService.dto.BillListResponse;
import com.example.spa_case.service.billService.dto.BillSaveRequest;
import com.example.spa_case.service.billService.response.BillAdminListResponse;
import com.example.spa_case.service.productService.response.ProductListResponse;
import com.example.spa_case.service.request.SelectOptionRequest;
import com.example.spa_case.util.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class BillService {

    private final BillRepository billRepository;
    private final BillProductRepository billProductRepository;
    private final ProductRepository productRepository;
    private final BillComboRepository billComboRepository;
    private final ComboRepository comboRepository;

    private final BillAdminRepository billAdminRepository;



    public void create(BillSaveRequest request) {
        var bill = AppUtil.mapper.map(request, Bill.class);
        BigDecimal totalBillPrice = BigDecimal.valueOf(0.00);

        bill = billRepository.save(bill);
        Bill finalBill = bill;
        List<Long> idProducts = request.getIdProduct().stream().map(Long::valueOf).toList();
        billProductRepository.saveAll(productRepository.findAllById(idProducts).stream().map(product -> {
           var result =  new BillProduct(finalBill,product);
           result.setProductName(product.getName());
           result.setPrice(product.getPrice());
            return result;
        }).collect(Collectors.toList()));

        List<Long> idCombos = request.getIdCombo().stream().map(Long::valueOf).toList();
        billComboRepository.saveAll(comboRepository.findAllById(idCombos).stream().map(combo -> {
            var result =  new BillCombo(finalBill,combo);
            result.setComboName(combo.getName());
            result.setPrice(combo.getPrice());
            return result;
        }).collect(Collectors.toList()));


    }


    public Page<BillListResponse> getBills(Pageable pageable) {
        Page<Bill> billPage = billRepository.findAll(pageable);
        Page<BillListResponse> billListResponses = billPage.map(bill -> {
            BillListResponse billListResponse = AppUtil.mapper.map(bill, BillListResponse.class);

            BigDecimal totalProductPrice = bill.getBillProducts()
                    .stream()
                    .map(billProduct -> billProduct.getProduct().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            billListResponse.setTotalProductPrice(totalProductPrice);

            BigDecimal totalComboPrice = bill.getBillCombos()
                    .stream()
                    .map(billCombo -> billCombo.getCombo().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            billListResponse.setTotalComboPrice(totalComboPrice);

            billListResponse.setProducts(bill.getBillProducts()
                    .stream()
                    .map(billProduct -> {
                        String productName = billProduct.getProduct().getName();
                        BigDecimal productPrice = billProduct.getProduct().getPrice();
                        return productName + " (" + productPrice + ")";

                    })
                    .collect(Collectors.toList()));

            billListResponse.setCombos(bill.getBillCombos()
                    .stream()
                    .map(billCombo -> {
                        String comboName = billCombo.getCombo().getName();
                        BigDecimal comboPrice = billCombo.getCombo().getPrice();
                        return comboName + " (" + comboPrice + ")";
                    })
                    .collect(Collectors.toList()));

            return billListResponse;
        });
        return billListResponses;
    }




    public void update(BillSaveRequest request, Long id){
        var billDb = billRepository.findById(id).orElse(new Bill());
//        billDb.setType(new Type());
        AppUtil.mapper.map(request,billDb);
        billProductRepository.deleteAll(billDb.getBillProducts());


        var billProduct = new ArrayList<BillProduct>();
        for (String idProduct : request.getIdProduct()) {
            Product product = new Product(Long.valueOf(idProduct));
            billProduct.add(new BillProduct(billDb, product));
        }
        billProductRepository.saveAll(billProduct);
        billRepository.save(billDb);
    }



    public BillDetailResponse findById(Long id){
        var bill = billRepository.findById(id).orElse(new Bill());
        var result = AppUtil.mapper.map(bill, BillDetailResponse.class);
        result.setProductIds(bill
                .getBillProducts()
                .stream().map(roomCategory -> roomCategory.getProduct().getId())
                .collect(Collectors.toList()));
        return result;
    }
    @Transactional
    public void delete(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        billProductRepository.deleteByBill(bill);
        billRepository.delete(bill);
    }


    public Page<BillAdminListResponse> getAll(String search, Pageable pageable, BigDecimal min, BigDecimal max) {
        search = "%" + search + "%";
        return billAdminRepository.searchAllByService(search, pageable, min, max)
                .map(bill -> BillAdminListResponse.builder()
                        .id(bill.getId())
                        .user(bill.getUser().getName())
                        .customerName(bill.getCustomerName())
                        .customerEmail(bill.getCustomerEmail())
                        .customerPhone(bill.getCustomerPhone())
                        .customerQuantity(bill.getCustomerQuantity())
                        .appointmentTime(bill.getAppointmentTime())
                        .price(bill.getPrice())
                        .products(bill.getBillProducts()
                                .stream()
                                .map(comboProduct -> comboProduct.getProduct().getName())
                                .collect(Collectors.joining(", ")))
                        .combos(bill.getBillCombos()
                                .stream()
                                .map(billCombo -> billCombo.getCombo().getName())
                                .collect(Collectors.joining(" ")))// Chuyển thành chuỗi
                        .build());
    }
}
