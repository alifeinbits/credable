package com.credable.lms.controller;

import com.credable.lms.model.Loan;
import com.credable.lms.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @PostMapping("/request")
    @Operation(summary = "Request a loan", description = "Submit a loan request for a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan request submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or missing client-id"),
            @ApiResponse(responseCode = "409", description = "Customer already has a pending loan"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> requestLoan(
            @RequestHeader(value = "client-id", required = true) String clientId,
            @RequestParam String customerNumber,
            @RequestParam double amount) {
        if (clientId == null || clientId.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing client-id header");
        }
        // Process the loan request
        Loan loan = loanService.requestLoan(customerNumber, amount, Long.valueOf(clientId));
        return ResponseEntity.ok(loan);
    }
}
