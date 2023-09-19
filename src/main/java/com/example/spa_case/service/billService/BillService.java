package com.example.spa_case.service.billService;

import com.example.spa_case.model.Bill;
import com.example.spa_case.model.BillCombo;
import com.example.spa_case.model.BillProduct;
import com.example.spa_case.model.Product;
import com.example.spa_case.repository.*;
import com.example.spa_case.service.billService.dto.BillDetailResponse;
import com.example.spa_case.service.billService.dto.BillListResponse;
import com.example.spa_case.service.billService.dto.BillSaveRequest;
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





    public void create(BillSaveRequest request) {
        var bill = AppUtil.mapper.map(request, Bill.class);
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
                    .map(billCombo -> billCombo.getCombo().getName())
                    .collect(Collectors.toList()));

            return billListResponse;
        });
        return billListResponses;
    }


    // Hàm getAll không cần phân trang (không xóa)
//    public List<BillListResponse> getBills(){
//        return billRepository.findAll().stream().map(e -> {
//            var result = AppUtil.mapper.map(e, BillListResponse.class);
////            result.setType(e.getType().getName());
//            result.setProducts(e.getBillProducts()
//                    .stream().map(c -> c.getProduct().getName())
//                    .collect(Collectors.joining(", ")));
//            return result;
//        }).collect(Collectors.toList());
//    }

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
}
