package com.example.spa_case.service;

import com.example.spa_case.model.Customer;
import com.example.spa_case.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }


    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public void deleteCustomer(Long customerId) {

        customerRepository.deleteById(customerId);
    }





}
