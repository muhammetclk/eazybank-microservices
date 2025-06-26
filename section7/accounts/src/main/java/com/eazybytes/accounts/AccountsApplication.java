package com.eazybytes.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.eazybytes.accounts.dto.AccountContactInfoDto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	info=@Info(
		title="Accounts Microservice Rest APIs Docs",
	 version="1.0",
	  description="EazyBank Accounts Microservice",
	  contact = @Contact(name="Muhammet Celik", email="muhammetclk56@gmail.com", url="https://github.com/muhammetclk"),
	  license = @License(name="Apache 2.0", url="https://github.com/muhammetclk")),externalDocs = @ExternalDocumentation(description="EazyBank Accounts Rest api docs",url="https://github.com/muhammetclk"))

public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
