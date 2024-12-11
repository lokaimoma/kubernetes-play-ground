package com.koc.authservice;

import lombok.Data;

@Data
public class LoanPayload {
    private String userId;
    private String bookId;
}
