package com.koc.authservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserRepresentation toUserRepresentation() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email.substring(0, email.indexOf(".")));
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
}
