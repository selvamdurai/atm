package com.atm.model;

import java.io.Serializable;

public class WithdrawRequest implements Serializable {
    private  String account;
    private int amount;

    public WithdrawRequest(String account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String accountNum) {
        this.account = accountNum;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
