package com.credable.lms.service;

import com.credable.lms.exception.CustomerNotFoundException;
import com.credable.lms.model.Customer;
import com.credable.lms.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByNumber(String customerNumber) {
        return customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }
}
