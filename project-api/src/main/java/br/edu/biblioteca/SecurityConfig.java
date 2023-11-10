package br.edu.biblioteca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	private static final String RESOURCE_ID = "project-api";
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			// auth.requestMatchers("/api/**").permitAll();
			auth.anyRequest().authenticated();
		});
		http.csrf(csrf -> csrf.disable());
		http.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter1())));
		return http.build();
	}

	private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter1() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter2() {
		return jwt -> {
			
			var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
			var resourceRoles = new ArrayList<>();
			
			if (resourceAccess.containsKey(RESOURCE_ID)) {
				var resourceMap = (Map<String, List<String>>) resourceAccess.get(RESOURCE_ID);
				resourceMap.get("roles").forEach(role -> resourceRoles.add(role));
			}
			   
			List<SimpleGrantedAuthority> grants = resourceRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
			System.out.println(grants);
			return new JwtAuthenticationToken(jwt, grants);
		};
	}

}
