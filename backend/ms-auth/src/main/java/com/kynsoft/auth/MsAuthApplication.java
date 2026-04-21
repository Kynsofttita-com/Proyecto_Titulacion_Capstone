package com.kynsoft.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "ms-auth API",
				version = "1.0.0",
				description = "Authentication and User Management microservice for the Capstone platform"
		),
		security = @SecurityRequirement(name = "bearerAuth")
)
public class MsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthApplication.class, args);
	}

}
