package com.eazytbytes.gatewayserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

     @RequestMapping("/contactSupport")
     public Mono<ResponseEntity<String>> contactSupport() {
        return Mono.just(
            ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE) 
                .body("An error occurred. Please try again later or contact support.")
        );
    }

    
}
