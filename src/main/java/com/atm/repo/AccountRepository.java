package com.atm.repo;


import com.atm.model.BankAccount;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountRepository {
    public AccountRepository(Map<String, BankAccount> accounts) {
        this.accounts = accounts;
    }

    private Map<String, BankAccount> accounts = new HashMap<>();
    public Map<String, BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, BankAccount> accounts) {
        this.accounts = accounts;
    }

    public void saveAll(Map accounts) {
        this.accounts=accounts;
    }

    public void save(BankAccount account) {
        String accountNumber = account.getAccountNumber();
        accounts.put(accountNumber,account);
    }
}
