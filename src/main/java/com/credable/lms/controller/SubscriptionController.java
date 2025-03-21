package com.credable.lms.controller;

import com.credable.lms.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@Tag(name = "Subscription", description = "Customer subscription API")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{customerNumber}")
    @Operation(summary = "Subscribe customer", description = "Registers a customer for loan services.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer subscribed successfully"),
            @ApiResponse(responseCode = "409", description = "Customer already subscribed"),
            @ApiResponse(responseCode = "404", description = "KYC details not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> subscribe(@PathVariable String customerNumber) {
        return ResponseEntity.ok(subscriptionService.subscribeCustomer(customerNumber));
    }
}
