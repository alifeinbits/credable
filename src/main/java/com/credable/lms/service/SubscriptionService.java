package com.credable.lms.service;

import com.credable.lms.exception.BusinessException;
import com.credable.lms.exception.KycServiceException;
import com.credable.lms.model.Customer;
import com.credable.lms.model.CustomerStatus;
import com.credable.lms.repository.CustomerRepository;
import com.credable.lms.response.KycResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {
    private final CustomerRepository customerRepository;
    private final KycService kycService;

    public SubscriptionService(CustomerRepository customerRepository, KycService kycService) {
        this.customerRepository = customerRepository;
        this.kycService = kycService;
    }

    @Transactional
    public String subscribeCustomer(String customerNumber) {
        // Check if customer is already subscribed
        if (customerRepository.findByCustomerNumber(customerNumber).isPresent()) {
            throw new BusinessException("Customer is already subscribed.");
        }

        // Fetch customer details from SOAP KYC API
        KycResponse kycResponse = kycService.getKycDetails(customerNumber);
        if (kycResponse == null || kycResponse.getName() == null) {
            throw new KycServiceException("KYC details not found for customer.");
        }

        // Save customer in the database
        Customer customer = new Customer();
        customer.setName(kycResponse.getName());
        customer.setPhoneNumber(kycResponse.getPhoneNumber());
        customer.setCustomerNumber(customerNumber);
        customer.setStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer);

        return "Customer " + customerNumber + " subscribed successfully.";
    }
}

