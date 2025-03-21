package com.credable.lms.repository;

import com.credable.lms.model.ClientRegistration;
import com.credable.lms.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRegistrationRepository extends JpaRepository<ClientRegistration, Long> {
    ClientRegistration findClientRegistrationById(Long id);
}
