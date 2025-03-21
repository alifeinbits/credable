package com.credable.lms.service;

import com.credable.lms.exception.LoanInProgressException;
import com.credable.lms.exception.MaxRetriesExceededException;
import com.credable.lms.exception.ResourceNotFoundException;
import com.credable.lms.model.ClientRegistration;
import com.credable.lms.model.Customer;
import com.credable.lms.model.Loan;
import com.credable.lms.model.LoanStatus;
import com.credable.lms.repository.ClientRegistrationRepository;
import com.credable.lms.repository.LoanRepository;
import com.credable.lms.response.ScoreResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final ScoringEngineService scoringEngineService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public LoanService(LoanRepository loanRepository, CustomerService customerService, ScoringEngineService scoringEngineService, ClientRegistrationRepository clientRegistrationRepository) {
        this.loanRepository = loanRepository;
        this.customerService = customerService;
        this.scoringEngineService = scoringEngineService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    public Loan requestLoan(String customerNumber, double amount,Long clientId) {
        Customer customer = customerService.getCustomerByNumber(customerNumber);
        ClientRegistration clientRegistration = clientRegistrationRepository.findClientRegistrationById(clientId);
        if (clientRegistration == null) {
            throw new ResourceNotFoundException("Client not found");
        }
        // Prevent duplicate loan requests
        loanRepository.findByCustomerAndStatus(customer, LoanStatus.PROCESSING)
                .ifPresent(existingLoan -> { throw new LoanInProgressException("Existing loan request in progress"); });

        // Initiate Scoring Engine process
        String token = scoringEngineService.initiateQueryScore(customerNumber,clientRegistration.getToken());

        // Fetch Score with retry
        ScoreResponse scoreResponse = scoringEngineService.fetchScore(token);

        if (scoreResponse.getLimitAmount().doubleValue() >= amount) {
            Loan loan = new Loan();
            loan.setCustomer(customer);
            loan.setAmount(amount);
            loan.setStatus(LoanStatus.PROCESSING);
            loan.setRequestDate(LocalDateTime.now());
            return loanRepository.save(loan);
        } else {
            throw new MaxRetriesExceededException("Loan request exceeds limit");
        }
    }

    public Loan getLoanStatus(String customerNumber) {
        Customer customer = customerService.getCustomerByNumber(customerNumber);
        return loanRepository.findByCustomerAndStatus(customer, LoanStatus.PROCESSING)
                .orElseThrow(() -> new ResourceNotFoundException("No active loan"));
    }
}

