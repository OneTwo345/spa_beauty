package com.example.spa_case.service.customerService;

import com.example.spa_case.controller.exception.ResourceNotFoundException;
import com.example.spa_case.model.Customer;
import com.example.spa_case.model.User;
import com.example.spa_case.repository.CustomerRepository;
import com.example.spa_case.service.customerService.response.CustomerListResponse;
import com.example.spa_case.service.userService.response.UserListResponse;
import com.example.spa_case.util.AppMessage;
import com.example.spa_case.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }
    public Page<CustomerListResponse> getAll(String search , Pageable pageable) {
        search = "%"+search+"%";
        return customerRepository.searchAllCustomer(search, pageable)
                .map(book -> CustomerListResponse.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .email(book.getEmail())
                        .phone(book.getPhone())
                        .statusCustomer(String.valueOf(book.getType()))
                        .build());
    }

    public Customer findById(Long customerId){
        //de tai su dung
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "Customer", customerId)));
    }
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }


    public void deleteCustomerId(Long customerId) {

        Customer customer = findById(customerId);
        customerRepository.delete(customer);
    }





}
