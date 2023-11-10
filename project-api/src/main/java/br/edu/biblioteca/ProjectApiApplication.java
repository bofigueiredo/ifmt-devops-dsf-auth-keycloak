package br.edu.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//jsr250Enabled  -> @RolesAllowed, @PermitAlle @DenyAll
//securedEnabled -> @Secured
//prePostEnabled -> @PreAuthorize, @PostAuthorize


@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@SpringBootApplication
public class ProjectApiApplication {
	
//	AuthorizationManagerBeforeMethodInterceptor

	public static void main(String[] args) {
		SpringApplication.run(ProjectApiApplication.class, args);
	}

}
