package com.example.spa_case.service.billService;

import com.example.spa_case.model.Bill;
import com.example.spa_case.model.BillProduct;
import com.example.spa_case.model.Product;
import com.example.spa_case.repository.BillProductRepository;
import com.example.spa_case.repository.BillRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class BillService {

    private final BillRepository billRepository;
    private final BillProductRepository billProductRepository;




    public void create(BillSaveRequest request) {
        var bill = AppUtil.mapper.map(request, Bill.class);
        bill = billRepository.save(bill);
        Bill finalBill = bill;
        billProductRepository.saveAll(request
                .getIdProduct()
                .stream()
                .map(id -> new BillProduct(finalBill,new Product(Long.valueOf(id))))
                .collect(Collectors.toList()));
    }

    public Page<BillListResponse> getBills(Pageable pageable){
        return billRepository.findAll(pageable).map(e -> {
            var result = AppUtil.mapper.map(e, BillListResponse.class);
//            result.setType(e.getType().getName());
            result.setProducts(e.getBillProducts()
                    .stream().map(c -> c.getProduct().getName())
                    .collect(Collectors.joining(", ")));
            return result;
        });
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
}
