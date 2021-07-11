package com.atm.model;

public class AccountBalance {
    private String accountNumber;
    private long balance;
    private String message;

    public AccountBalance(String accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public AccountBalance() {
    }

    public AccountBalance(String accountNumber, long balance, String message) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.message = message;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
