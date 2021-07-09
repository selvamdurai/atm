package com.atm.model;



import java.io.Serializable;
import java.util.Map;


public class WithdrawResponse implements Serializable {
    private Map<Currency,Integer> notes;
    private BankAccount account;

//    private String fifties;
//    private String twenties;
//    private String tens;
//    private String ones;
//    private String accountNumber;
//    private String accountBalance;
    private String message;

    public WithdrawResponse(Map<Currency, Integer> notesDispensed, BankAccount account ) {
        this.account = account;
        this.notes = notesDispensed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Currency, Integer> getNotes() {
        return notes;
    }

    public void setNotes(Map<Currency, Integer> notes) {
        this.notes = notes;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }
}
