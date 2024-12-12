package com.koc.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserIn {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;

    public UserRepresentation toUserRepresentation() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(userNameFromEmail(email));
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        List<CredentialRepresentation> credRep = new ArrayList<>();
        CredentialRepresentation cr = new CredentialRepresentation();
        cr.setValue(password);
        cr.setTemporary(false);
        credRep.add(cr);
        userRepresentation.setCredentials(credRep);
        return userRepresentation;
    }

    public static String userNameFromEmail(String email) {
        return email.substring(0, email.indexOf("."));
    }
}
