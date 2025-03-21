package com.credable.lms.controller;

import com.credable.lms.integration.cbs.transaction.TransactionData;
import com.credable.lms.response.RegisterClientResponse;
import com.credable.lms.service.ScoringService;
import com.credable.lms.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scoring")
public class ScoringController {
    private final TransactionService transactionService;
    private final ScoringService scoringService;

    public ScoringController(TransactionService transactionService, ScoringService scoringService) {
        this.transactionService = transactionService;
        this.scoringService = scoringService;
    }
    @PostMapping("/register")
    @Operation(summary = "Register LMS with Scoring Engine", description = "Registers LMS with the scoring engine to expose transaction data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RegisterClientResponse> registerClient(
            @RequestParam String endpointUrl,
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String password) {

        return ResponseEntity.ok(scoringService.registerClient(endpointUrl, name, username, password));
    }

    @GetMapping("/transactions/{customerNumber}")
    public ResponseEntity<List<TransactionData>> getTransactions(@PathVariable String customerNumber) {
        return ResponseEntity.ok(transactionService.getTransactions(customerNumber).getTransactions());
    }
}

