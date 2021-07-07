package com.atm.repo;


import com.atm.model.BankAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountRepository {
    private Map<String, BankAccount> accounts = new HashMap<>();


    void updateAccountBalance(String accountNumber, double newBalance){

    }
    public double getBalance(String accountNumber){
        return this.accounts.get(accountNumber).getBalance();
    }

    public void updateBalance(String accountNumber, double balance){
        this.accounts.get(accountNumber).setBalance(balance);
    }

    public void saveAll(Map accounts) {
        this.accounts=accounts;
    }
}
