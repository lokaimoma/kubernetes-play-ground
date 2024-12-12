package com.koc.authservice.services;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "keycloak")
@Setter
@Getter
public class KeycloakService {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;

    Keycloak adminKeyCloak;

    public Keycloak getAdminKeycloak() {
        if (adminKeyCloak == null) {
            adminKeyCloak = KeycloakBuilder.builder().serverUrl(serverUrl).clientId(clientId).clientSecret(clientSecret)
                    .grantType("client_credentials").realm(realm).build();
        }
        return adminKeyCloak;
    }

    public Keycloak getKeyCloakFromUserCredentials(String username, String password) {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .realm(realm)
                .username(username)
                .password(password)
                .build();
    }
}
