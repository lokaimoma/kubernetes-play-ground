package com.koc.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.authorizeExchange(exchange -> exchange.pathMatchers("/umgmt/auth/signup", "/umgmt/auth/login", "/c/**")
                        .permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(serverSpec -> {
                    serverSpec.jwt(jwtSpec -> {
                        jwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter());
                    });
                });
        return http.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var realmRoles = Optional.ofNullable((Map<String, Object>) jwt.getClaims().get("realm_roles"));
            var roles = realmRoles.flatMap(r -> Optional.ofNullable((List<String>) r.get("roles")));
            var stream = roles.stream().flatMap(Collection::stream)
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast);
            return Flux.fromStream(stream);
        });
        return converter;
    }
}
