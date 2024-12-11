package com.koc.authservice;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookLoanRepository extends CrudRepository<BookLoan, UUID> {
    Optional<BookLoan> findByUserIdAndBookId(String userId, String bookId);
}
