package com.koc.authservice.controllers;

import com.koc.authservice.dto.BookloanAdminRecords;
import com.koc.authservice.dto.BookloanDTO;
import com.koc.authservice.feignClients.BookMgmtService;
import com.koc.authservice.services.BookLoanService;
import com.koc.authservice.dto.LoanPayload;
import feign.FeignException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService service;
    private final BookMgmtService bookMgmtService;

    @GetMapping
    public ResponseEntity<Iterable<BookloanDTO>> getLoanRecords(@RequestParam(name = "size", defaultValue = "20") @Min(1) int size,
                                                                @RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
                                                                @RequestParam(name = "email") @Email @NotNull String email
    ) {
        Iterable<BookloanDTO> bls = service.getBookLoans(size, page, email);
        return ResponseEntity.ok(bls);
    }

    @GetMapping("/admin")
    public ResponseEntity<Iterable<BookloanAdminRecords>> getBorrowRecords(@RequestParam(name = "size", defaultValue = "20") @Min(1) int size,
                                                                           @RequestParam(name = "page", defaultValue = "0") @Min(0) int page) {
        return ResponseEntity.ok(service.getBorrowRecords(size, page));
    }

    @PostMapping
    public ResponseEntity<String> loanBook(@RequestBody @Valid LoanPayload payload) {
        try {
            if (service.verifyPendingLoan(payload)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Load record already exists");
            }
            BookMgmtService.BorrowBookResponse resp = bookMgmtService.borrowBook(payload.getBookId());
            log.info("Borrow response for {} = {}", payload.getBookId(), resp);

            BookLoanService.LoanResponse response = service.addBookLoan(payload);
            return switch (response) {
                case USER_NOT_EXIST -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
                case RECORD_CREATED -> ResponseEntity.status(HttpStatus.CREATED).body("Loan record saved");
            };
        } catch (FeignException.BadRequest e) {
            log.error("Failed to borrow book, Reason={}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody @Valid LoanPayload payload) {
        try {
            BookMgmtService.ReturnBookResponse resp = bookMgmtService.returnBook(payload.getBookId());
            log.info("Return book response for {} = {}", payload.getBookId(), resp);
            service.returnBook(payload);
            return ResponseEntity.status(HttpStatus.OK).body("Loan record updated");
        } catch (FeignException.BadRequest e) {
            log.error("Failed to return book, Reason={}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
