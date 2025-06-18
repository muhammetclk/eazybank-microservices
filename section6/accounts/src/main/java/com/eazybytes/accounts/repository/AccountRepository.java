package com.eazybytes.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.eazybytes.accounts.entity.Account;

import jakarta.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findByCustomerCustomerId(Long customerId);

    @Transactional
    @Modifying
    public void deleteByCustomerCustomerId(Long customerId);
}
