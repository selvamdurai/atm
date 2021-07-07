package com.atm.model;


public class BankAccount {
    private String accountNumber;
    private int pin;
    private double balance;
    private double overdraftLimit;

    public BankAccount(String accountNumber, int pin, double balance, double overdraftLimit) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
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
