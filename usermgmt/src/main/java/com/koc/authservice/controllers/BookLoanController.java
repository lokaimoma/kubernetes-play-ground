package com.koc.authservice.controllers;

import com.koc.authservice.entities.BookLoan;
import com.koc.authservice.services.BookLoanService;
import com.koc.authservice.dto.LoanPayload;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService service;

    @GetMapping
    public ResponseEntity<Iterable<BookLoan>> getLoanRecords(@RequestParam(name = "size", defaultValue = "20") @Min(1) int size,
                                                             @RequestParam(name = "page", defaultValue = "0") @Min(0) int page
    ) {
        Iterable<BookLoan> bls = service.getBookLoans(size, page);
        return ResponseEntity.ok(bls);
    }

    @PostMapping
    public ResponseEntity<String> loanBook(@RequestBody @Valid LoanPayload payload) {
        BookLoanService.LoanResponse response = service.addBookLoan(payload);

        return switch (response) {
            case BOOK_ALREADY_LOANED ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Load record already exists");
            case USER_NOT_EXIST -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
            case RECORD_CREATED -> ResponseEntity.status(HttpStatus.CREATED).body("Loan record saved successfully");

        };
    }

    @PostMapping("/return")
    public ResponseEntity<Void> returnBook(@RequestBody @Valid LoanPayload payload) {
        service.returnBook(payload);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
