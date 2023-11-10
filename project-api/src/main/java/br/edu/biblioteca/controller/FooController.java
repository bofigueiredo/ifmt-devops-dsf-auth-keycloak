package br.edu.biblioteca.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/foo")
public class FooController {
	
	@GetMapping
	@RolesAllowed("GET_FOO")
	public String action1(@AuthenticationPrincipal Jwt jwt) {
		return "EXECUTANDO ACTION 1 COMO: " + jwt.getClaimAsString("preferred_username");
	}
	
	@PostMapping
	@RolesAllowed("POST_FOO")
	public String action2(@AuthenticationPrincipal Jwt jwt) {
		return "EXECUTANDO ACTION 2 COMO: " + jwt.getClaimAsString("preferred_username");
	}
	
	@PutMapping
	@RolesAllowed("PUT_FOO")
	public String action3(@AuthenticationPrincipal Jwt jwt) {
		return "EXECUTANDO ACTION 3 COMO: " + jwt.getClaimAsString("preferred_username");
	}

}
