package com.koc.authservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Service;

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

    private final KeycloakUtil keycloakUtil;
    private final BookLoanRepository repository;

    public LoanResponse addBookLoan(LoanPayload loanPayload) {
        Optional<BookLoan> optBl = repository.findByUserIdAndBookId(loanPayload.getUserId(), loanPayload.getBookId());
        if (optBl.isPresent()) {
            return LoanResponse.BOOK_ALREADY_LOANED;
        }

        Keycloak kc = keycloakUtil.getKeycloak();
        UserResource user = kc.realm(keycloakUtil.getRealm()).users().get(loanPayload.getUserId());
        if (user == null) {
            return LoanResponse.USER_NOT_EXIST;
        }

        BookLoan bl = BookLoan.builder().bookId(loanPayload.getBookId()).userId(loanPayload.getUserId()).build();
        repository.save(bl);

        return LoanResponse.RECORD_CREATED;
    }
}
