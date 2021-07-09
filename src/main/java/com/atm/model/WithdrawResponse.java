package com.atm.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;


public class WithdrawResponse implements Serializable {
    private Map<Currency,Integer> notesDispensed;
    private BankAccount account;
    private String message;

    public WithdrawResponse(Map<Currency, Integer> notesDispensed, BankAccount account ) {
        this.account = account;
        this.notesDispensed = notesDispensed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
