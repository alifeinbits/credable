package com.credable.lms.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class LoanResponse {
    private String loanId;
    private String customerNumber;
    private String productName;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private Integer tenureInDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal processingFee;
    private BigDecimal totalRepaymentAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ScoreDetails scoreDetails;
}
