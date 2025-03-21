package com.credable.lms.model;

import com.credable.lms.integration.cbs.kyc.Status;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String customerNumber;

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phoneNumber;
    Status status;

}
