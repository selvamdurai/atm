package com.atm.model;



import java.io.Serializable;
import java.math.BigDecimal;


public class BankAccount implements Serializable {
    private String accountNumber;

    private BigDecimal balance;
    private BigDecimal overdraftLimit;

    public BankAccount(String accountNumber, BigDecimal balance, BigDecimal overdraftLimit) {
        this.accountNumber = accountNumber;

        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
    }

    public void withdraw(BigDecimal amount){
       this.balance = this.balance.subtract(amount);
    }
    public void deposit(BigDecimal amount){
        this.balance = this.balance.add(amount);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber +
                ", balance=" + balance +
                ", overdraftLimit=" + overdraftLimit +
                '}';
    }
}
