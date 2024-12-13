package com.koc.authservice.controllers;

import com.koc.authservice.dto.UserLoginPayload;
import com.koc.authservice.services.KeycloakService;
import com.koc.authservice.dto.UserIn;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final KeycloakService keycloakService;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserIn user) {
        Keycloak kc = keycloakService.getAdminKeycloak();
        log.info("Getting user representation for: {}", user.toString());
        UserRepresentation ur = user.toUserRepresentation();
        try (Response res = kc.realm(keycloakService.getRealm()).users().create(ur)) {
            log.info(res.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<AccessTokenResponse>> loginUser(@RequestBody @Valid UserLoginPayload up) {
        Keycloak userKc = keycloakService.getKeyCloakFromUserCredentials(UserIn.userNameFromEmail(
                up.getEmail()), up.getPassword());
        try {
            AccessTokenResponse at = userKc.tokenManager().getAccessToken();
            return ResponseEntity.ok(Optional.of(at));
        } catch (BadRequestException | NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
