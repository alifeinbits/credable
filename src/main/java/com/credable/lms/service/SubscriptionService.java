package com.credable.lms.service;

import com.credable.lms.exception.BusinessException;
import com.credable.lms.exception.KycServiceException;
import com.credable.lms.integration.cbs.kyc.CustomerResponse;
import com.credable.lms.model.Customer;
import com.credable.lms.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {
    private final CustomerRepository customerRepository;
    private final KycSoapClientService kycSoapClientService;

    public SubscriptionService(CustomerRepository customerRepository, KycSoapClientService kycSoapClientService) {
        this.customerRepository = customerRepository;
        this.kycSoapClientService = kycSoapClientService;
    }

    @Transactional
    public String subscribeCustomer(String customerNumber) {
        // Check if customer is already subscribed
        if (customerRepository.findByCustomerNumber(customerNumber).isPresent()) {
            throw new BusinessException("Customer is already subscribed.");
        }

        // Fetch customer details from SOAP KYC API
        CustomerResponse kycResponse = kycSoapClientService.getKycDetails(customerNumber);
        if (kycResponse == null || kycResponse.getCustomer().getFirstName() == null) {
            throw new KycServiceException("KYC details not found for customer.");
        }

        // Save customer in the database
        Customer customer = new Customer();
        customer.setFirstName(kycResponse.getCustomer().getFirstName());
        customer.setLastName(kycResponse.getCustomer().getFirstName());
        customer.setPhoneNumber(kycResponse.getCustomer().getMobile());
        customer.setCustomerNumber(kycResponse.getCustomer().getCustomerNumber());
        customer.setStatus(kycResponse.getCustomer().getStatus());
        customer.setEmail(kycResponse.getCustomer().getEmail());
        customer.setGender(kycResponse.getCustomer().getGender().value());
        customerRepository.save(customer);

        return "Customer " + customerNumber + " subscribed successfully.";
    }
}

