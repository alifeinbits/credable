package com.credable.lms.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public  class LoanStatusResponse {
    private Long loanId;
    private String customerNumber;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private String status;
    private LocalDateTime requestDate;
    private LocalDateTime approvalDate;
    private LocalDateTime rejectionDate;
    private String rejectionReason;
}
