package com.koc.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanPayload {
    @Email
    @NotNull
    private String userEmail;
    @NotNull
    private String bookId;
}
