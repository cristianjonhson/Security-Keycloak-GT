package cl.edu.galaxy.training.apps.siac.api_gestion_academica.configuration;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clase de configuración para establecer la seguridad en la aplicación.
 * Esta clase habilita la seguridad web y la seguridad de métodos, y configura
 * la cadena de filtros de seguridad y el convertidor de autenticación JWT para
 * Keycloak.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${keycloak.resource}")
    private String clientId; // ID del cliente en Keycloak

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @param http objeto HttpSecurity para configurar la seguridad.
     * @return la cadena de filtros de seguridad configurada.
     * @throws Exception si ocurre un error al configurar la seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/test/libre/**").permitAll()
                        .requestMatchers("/api/v1/companies/private/**").hasAuthority("admin")
                        .requestMatchers("/api/v1/test/private/endpoint1").hasAuthority("admin")
                        .requestMatchers("/api/v1/test/private/endpoint2").hasAuthority("director_Academico")
                        .requestMatchers("/api/v1/test/private/endpoint3").hasAuthority("profesor")
                        .requestMatchers("/api/v1/test/private/endpoint5").hasAuthority("DEMO")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak())));

        return http.build();
    }

    /**
     * Configura el convertidor de autenticación JWT para Keycloak.
     *
     * @return el convertidor de autenticación JWT configurado.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return jwtAuthenticationConverter;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Set<String> roles = new LinkedHashSet<>();
        roles.addAll(extractRoles(jwt.getClaim("realm_access")));

        Object resourceAccessClaim = jwt.getClaim("resource_access");
        if (resourceAccessClaim instanceof Map<?, ?> resourceAccess) {
            roles.addAll(extractRoles(resourceAccess.get(clientId)));
        }

        log.debug("Authorities extraidas para {}: {}", jwt.getSubject(), roles);

        return roles.stream()
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private List<String> extractRoles(Object claimSection) {
        if (!(claimSection instanceof Map<?, ?> claimMap)) {
            return List.of();
        }

        Object roles = claimMap.get("roles");
        if (!(roles instanceof Collection<?> roleCollection)) {
            return List.of();
        }

        return roleCollection.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }

}
