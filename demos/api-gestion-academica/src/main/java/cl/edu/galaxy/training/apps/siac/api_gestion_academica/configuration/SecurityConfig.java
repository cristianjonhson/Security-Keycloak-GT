package cl.edu.galaxy.training.apps.siac.api_gestion_academica.configuration;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import org.springframework.security.web.SecurityFilterChain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("unchecked")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Value("${keycloak.resource}")
	private String clienId; // Application

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		log.info("filterChain...");
		
		http.authorizeHttpRequests(auth -> {
			
			auth.requestMatchers("/api/v1/demos/**").permitAll();
			
			auth.requestMatchers("/api/v1/test/**","/api/v1/companies/**");
			
			auth.requestMatchers("/api/v1/companies/private/**").hasAuthority("admin").anyRequest().fullyAuthenticated();
			
			//auth.requestMatchers("/api/v1/companies/**").hasAnyAuthority("admin","director","profesor").anyRequest().fullyAuthenticated();
		
			
		}).oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

		return http.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
		Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
			
			log.info("jwtAuthenticationConverterForKeycloak...");
			/*
			Map<String, Object> claims = jwt.getClaims();

			for (String key : claims.keySet()) {
				log.info(" - key {} -> {}", key, claims.get(key));
			}*/
			
			
			Object realm = jwt.getClaim("realm_access");

			LinkedTreeMap<String, List<String>> realmRoleMap = (LinkedTreeMap<String, List<String>>) realm;

			List<String> realmRoles = new ArrayList<>(realmRoleMap.get("roles"));
			
			Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

			/*
			for (String key : resourceAccess.keySet()) {
				log.info("key {} -> {}", key, resourceAccess.get(key));
			}*/

			Object client = resourceAccess.get(clienId);

			LinkedTreeMap<String, List<String>> clientRoleMap = (LinkedTreeMap<String, List<String>>) client;

			List<String> clientRoles = new ArrayList<>(clientRoleMap.get("roles"));
			
			
			Collection<GrantedAuthority> realmListSimpleGrantedAuthority = realmRoles.stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			
			Collection<GrantedAuthority> clientListSimpleGrantedAuthority = clientRoles.stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

			realmListSimpleGrantedAuthority.addAll(clientListSimpleGrantedAuthority);
			
			//clientListSimpleGrantedAuthority.forEach(System.out::println);

			realmListSimpleGrantedAuthority.forEach(System.out::println);

			return realmListSimpleGrantedAuthority;
			//return clientListSimpleGrantedAuthority;
		};

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return jwtAuthenticationConverter;
	}
	
	/*
	@Bean
	public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
		return http.build();
	}*/
	

}
	