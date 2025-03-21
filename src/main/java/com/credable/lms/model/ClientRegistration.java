package com.credable.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "client_registrations")
@Getter
@Setter
public class ClientRegistration {
    @Id
    private Long id;

    private String url;
    private String name;
    private String username;
    private String password;
    private String token;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}