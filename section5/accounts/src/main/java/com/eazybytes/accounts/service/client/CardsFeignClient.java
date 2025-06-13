package com.eazybytes.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eazybytes.accounts.dto.CardDto;



@FeignClient(name = "cards")
public interface CardsFeignClient {


    @GetMapping(value = "/api/fetch",consumes = "application/json")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestParam  String mobileNumber);
    
}
