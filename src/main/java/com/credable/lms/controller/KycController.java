package com.credable.lms.controller;

import com.credable.lms.response.KycResponse;
import com.credable.lms.service.KycService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kyc")
@Tag(name = "KYC", description = "Customer KYC API")
public class KycController {
    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }

    @GetMapping("/{customerNumber}")
    @Operation(summary = "Fetch customer KYC", description = "Retrieve customer KYC details using SOAP API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "KYC details retrieved"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<KycResponse> getKyc(@PathVariable String customerNumber) {
        return ResponseEntity.ok(kycService.getKycDetails(customerNumber));
    }
}

