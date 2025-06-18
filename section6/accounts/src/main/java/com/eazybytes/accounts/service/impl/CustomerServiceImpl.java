package com.eazybytes.accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eazybytes.accounts.dto.AccountDto;
import com.eazybytes.accounts.dto.CardDto;
import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.LoanDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.ICustomerService;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {



    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
        Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
      Account account= accountRepository.findByCustomerCustomerId(customer.getCustomerId()).orElseThrow(()->new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));
     CustomerDetailsDto customerDetailsDto= CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));
       
       ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
       ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());
        customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
    
}
