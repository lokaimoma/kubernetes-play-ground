package com.koc.authservice.repositories;

import com.koc.authservice.entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookLoanRepository extends JpaRepository<BookLoan, UUID> {
    Optional<BookLoan> findByUserEmailAndBookIdAndReturnDateIsNull(String userEmail, String bookId);
}