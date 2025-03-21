package com.credable.lms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Data
public class Subscription {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_number")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}


