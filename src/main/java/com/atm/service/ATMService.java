package com.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.atm.repo.AccountRepository;


@Service
public class ATMService {
    private final AccountRepository accountRepository;

    @Autowired
    public ATMService(AccountRepository accountRepository) {
        this.employeeRepo = employeeRepo;
    }


}
