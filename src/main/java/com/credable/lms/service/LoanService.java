package com.credable.lms.service;

import com.credable.lms.exception.LoanInProgressException;
import com.credable.lms.exception.MaxRetriesExceededException;
import com.credable.lms.exception.ResourceNotFoundException;
import com.credable.lms.model.Customer;
import com.credable.lms.model.Loan;
import com.credable.lms.model.LoanStatus;
import com.credable.lms.repository.LoanRepository;
import com.credable.lms.response.ScoreResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final ScoringEngineService scoringEngineService;

    public LoanService(LoanRepository loanRepository, CustomerService customerService, ScoringEngineService scoringEngineService) {
        this.loanRepository = loanRepository;
        this.customerService = customerService;
        this.scoringEngineService = scoringEngineService;
    }

    public Loan requestLoan(String customerNumber, double amount) {
        Customer customer = customerService.getCustomerByNumber(customerNumber);

        // Prevent duplicate loan requests
        loanRepository.findByCustomerAndStatus(customer, LoanStatus.PROCESSING)
                .ifPresent(existingLoan -> { throw new LoanInProgressException("Existing loan request in progress"); });

        // Initiate Scoring Engine process
        String token = scoringEngineService.initiateQueryScore(customerNumber);

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

