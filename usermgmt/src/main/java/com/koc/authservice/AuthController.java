package com.koc.authservice;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final KeycloakUtil keycloakUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        Keycloak kc = keycloakUtil.getKeycloak();
        log.info("Getting user representation for: {}", user.toString());
        UserRepresentation ur = user.toUserRepresentation();
        try(Response res = kc.realm(keycloakUtil.getRealm()).users().create(ur)) {
            log.info(res.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User created successfully");
    }
}
