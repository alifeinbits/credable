package com.credable.lms.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String customerNumber;

    private String name;
    private String phoneNumber;

    public Customer() {}

    public Customer(String customerNumber, String name, String phoneNumber) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

}
