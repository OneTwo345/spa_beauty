package com.example.spa_case.service.billService;

import com.example.spa_case.model.*;
import com.example.spa_case.repository.*;
import com.example.spa_case.service.billService.dto.BillDetailResponse;
import com.example.spa_case.service.billService.dto.BillListResponse;
import com.example.spa_case.service.billService.dto.BillSaveRequest;
import com.example.spa_case.service.billService.request.BillAdminSaveRequest;
import com.example.spa_case.service.billService.response.BillAdminEditResponse;
import com.example.spa_case.service.billService.response.BillAdminListResponse;
import com.example.spa_case.service.comboService.response.ComboEditResponse;
import com.example.spa_case.service.productService.response.ProductListResponse;
import com.example.spa_case.service.request.SelectOptionRequest;
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
            var result = new BillProduct(finalBill, product);
            result.setProductName(product.getName());
            result.setPrice(product.getPrice());
            return result;
        }).collect(Collectors.toList()));

        List<Long> idCombos = request.getIdCombo().stream().map(Long::valueOf).toList();
        billComboRepository.saveAll(comboRepository.findAllById(idCombos).stream().map(combo -> {
            var result = new BillCombo(finalBill, combo);
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
        Page<BillAdminListResponse> result  = billAdminRepository.findByCustomerNameContainingIgnoreCaseAndPriceBetween(search, min, max, pageable)
                .map(bill -> BillAdminListResponse.builder()
                        .id(bill.getId())
                        .user(bill.getUser() != null ? bill.getUser().getName() : null)
                        .customerName(bill.getCustomerName())
                        .customerEmail(bill.getCustomerEmail())
                        .customerPhone(bill.getCustomerPhone())
                        .customerQuantity(bill.getCustomerQuantity())
                        .appointmentTime(bill.getAppointmentTime())
                        .price(bill.getPrice())
                        .idProduct(bill.getBillProducts()
                                .stream()
                                .map(comboProduct -> comboProduct.getProduct().getName())
                                .collect(Collectors.joining(", ")))
                        .idCombo(bill.getBillCombos()
                                .stream()
                                .map(billCombo -> billCombo.getCombo().getName())
                                .collect(Collectors.joining(", ")))
                        .build());
        return result;
    }
    public void createAdmin(BillAdminSaveRequest request) {
        var bill = AppUtil.mapper.map(request, Bill.class);
        BigDecimal totalBillPrice = BigDecimal.valueOf(0.00);
        billRepository.save(bill);

        // Tính tổng giá tiền của sản phẩm (products)
        List<Long> idProducts = request.getIdProduct().stream().map(Long::valueOf).toList();
        List<BillProduct> billProducts = new ArrayList<>(); // Danh sách sản phẩm
        for (Long productId : idProducts) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                BillProduct billProduct = new BillProduct(bill, product);
                billProduct.setProductName(product.getName());
                billProduct.setPrice(product.getPrice());
                billProducts.add(billProduct);
                totalBillPrice = totalBillPrice.add(product.getPrice());
            }
        }
        List<Long> idCombos = request.getIdCombo().stream().map(Long::valueOf).toList();
        List<BillCombo> billCombos = new ArrayList<>(); // Danh sách combo
        for (Long comboId : idCombos) {
            Combo combo = comboRepository.findById(comboId).orElse(null);
            if (combo != null) {
                BillCombo billCombo = new BillCombo(bill, combo);
                billCombo.setComboName(combo.getName());
                billCombo.setPrice(combo.getPrice());
                billCombos.add(billCombo);
                totalBillPrice = totalBillPrice.add(combo.getPrice());
            }
        }

        billProductRepository.saveAll(billProducts);
        billComboRepository.saveAll(billCombos);
        bill.setPrice(totalBillPrice);

        // Lưu đối tượng Bill vào cơ sở dữ liệu
         billRepository.save(bill);
    }
    public BillAdminEditResponse findByEdit(Long id){
        var bill = billRepository.findById(id).orElse(new Bill());
//        var result = AppUtil.mapper.map(bill,BillAdminEditResponse.class);
        var result = new BillAdminEditResponse();
        result.setId(bill.getId());
        result.setCustomerName(bill.getCustomerName());
        result.setAppointmentTime(bill.getAppointmentTime());
        result.setCustomerPhone(bill.getCustomerPhone());
        result.setCustomerQuantity(bill.getCustomerQuantity());
        result.setPrice(bill.getPrice());
        result.setUser(bill.getUser().getId());
        result.setIdProduct(bill.getBillProducts().stream()
                .map(product -> product.getProduct().getId())
                .collect(Collectors.toList()));
        result.setIdCombo(bill.getBillCombos()
                .stream().map(combo -> combo.getCombo().getId())
                .collect(Collectors.toList()));
        return result;
    }
    @Transactional
    public void update(BillAdminSaveRequest request, Long id) {
        var bill = billRepository.findById(id).orElse(new Bill());
        bill.setUser(new User());
        AppUtil.mapper.map(request, bill);
        billRepository.save(bill);
        billComboRepository.deleteAllById(bill.getBillCombos().stream()
                .map(BillCombo::getId)
                .collect(Collectors.toList()));
        billProductRepository.deleteAllById(bill.getBillProducts().stream()
                .map(BillProduct::getId)
                .collect(Collectors.toList()));
        saveCategoryAndActor(request, bill);

    }
    public void saveCategoryAndActor(BillAdminSaveRequest request, Bill room){
        var billProducts = new ArrayList<BillProduct>();
        for (String idProduct : request.getIdProduct()) {
            Product product = new Product(Long.valueOf(idProduct));
            billProducts.add(new BillProduct(room, product));
        }
        billProductRepository.saveAll(billProducts);
        var billCombos = new ArrayList<BillCombo>();
        for (String idCombo : request.getIdCombo()) {
            Combo combo = new Combo(Long.valueOf(idCombo));
            billCombos.add(new BillCombo(room, combo));
        }
        billComboRepository.saveAll(billCombos);
    }
    @Transactional
    public void deleteById(Long id) {
        billComboRepository.deleteAllByBillId(id);
        billProductRepository.deleteAllByBillId(id);
        billRepository.deleteBillById(id);
    }
}
