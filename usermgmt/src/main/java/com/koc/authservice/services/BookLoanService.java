package com.koc.authservice.services;

import com.koc.authservice.dto.UserIn;
import com.koc.authservice.entities.BookLoan;
import com.koc.authservice.dto.LoanPayload;
import com.koc.authservice.repositories.BookLoanRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookLoanService {

    public enum LoanResponse {
        BOOK_ALREADY_LOANED,
        USER_NOT_EXIST,
        RECORD_CREATED
    }

    private final KeycloakService keycloakService;
    private final BookLoanRepository repository;

    public Iterable<BookLoan> getBookLoans(int size, int pageNumber) {
        Pageable p = PageRequest.of(pageNumber, size, Sort.by(Sort.Order.desc("checkoutDate")));
        return repository.findAll(p);
    }

    public LoanResponse addBookLoan(LoanPayload loanPayload) {
        Optional<BookLoan> optBl = repository.findByUserEmailAndBookIdAndReturnDateIsNull(
                loanPayload.getUserEmail(), loanPayload.getBookId()
        );
        if (optBl.isPresent()) {
            return LoanResponse.BOOK_ALREADY_LOANED;
        }

        Keycloak kc = keycloakService.getAdminKeycloak();

        try {
            List<UserRepresentation> ur = kc.realm(keycloakService.getRealm()).users().search(
                    UserIn.userNameFromEmail(loanPayload.getUserEmail())
            );
            if (ur.isEmpty()) {
                throw new NotFoundException();
            }
        } catch (NotFoundException e) {
            return LoanResponse.USER_NOT_EXIST;
        }

        BookLoan bl = BookLoan.builder().bookId(loanPayload.getBookId()).userEmail(loanPayload.getUserEmail()).build();
        repository.save(bl);

        return LoanResponse.RECORD_CREATED;
    }

    public void returnBook(LoanPayload loanPayload) {
        Optional<BookLoan> optBl = repository.findByUserEmailAndBookIdAndReturnDateIsNull(
                loanPayload.getUserEmail(), loanPayload.getBookId()
        );
        if (optBl.isEmpty()) {
            return;
        }
        BookLoan bl = optBl.get();
        bl.setReturnDate(new Date());
        repository.save(bl);
    }
}
