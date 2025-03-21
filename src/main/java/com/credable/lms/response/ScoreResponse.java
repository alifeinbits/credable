package com.credable.lms.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ScoreResponse {
    private Long id;
    private String customerNumber;
    private Integer score;
    private BigDecimal limitAmount;
    private String exclusion;
    private String exclusionReason;
}
