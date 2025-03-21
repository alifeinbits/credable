package com.credable.lms.repository;

import com.credable.lms.model.Customer;
import com.credable.lms.model.Loan;
import com.credable.lms.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByCustomerAndStatus(Customer customer, LoanStatus status);
}

