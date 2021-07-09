package com.atm.service.commands;

import com.atm.model.BankAccount;

import java.math.BigDecimal;

public class AccountCommand implements Command{

    private BankAccount bankAccount;
    private BigDecimal amount;

    public AccountCommand(BankAccount bankAccount, BigDecimal amount) {
        this.bankAccount = bankAccount;
        this.amount = amount;
    }

    public void call() {
        bankAccount.withdraw(amount);

    }

    @Override
    public void rollback() {
        bankAccount.deposit(amount);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
