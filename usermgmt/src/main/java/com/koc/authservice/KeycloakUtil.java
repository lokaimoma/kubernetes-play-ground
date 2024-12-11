package com.koc.authservice;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "keycloak")
@Setter
@Getter
public class KeycloakUtil {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;

    Keycloak keycloak;

    public Keycloak getKeycloak() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder().serverUrl(serverUrl).clientId(clientId).clientSecret(clientSecret)
                    .grantType("client_credentials").realm(realm).build();
        }
        return keycloak;
    }
}
