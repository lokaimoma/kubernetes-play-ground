package com.koc.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginPayload {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}
