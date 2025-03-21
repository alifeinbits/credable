package com.credable.lms.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "score_results")
public class ScoreResult {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private Integer score;
    private BigDecimal limitAmount;
    private String exclusion;
    private String exclusionReason;

    private LocalDateTime createdAt;

}
