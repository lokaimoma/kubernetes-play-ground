package com.koc.authservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService service;

    @PostMapping
    public ResponseEntity<String> loanBook(@RequestBody LoanPayload payload) {
        BookLoanService.LoanResponse response = service.addBookLoan(payload);

        return switch (response) {
            case BOOK_ALREADY_LOANED ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Load record already exists");
            case USER_NOT_EXIST -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
            case RECORD_CREATED -> ResponseEntity.status(HttpStatus.CREATED).body("Loan record saved successfully");

        };
    }
}
