package com.atm.model;



import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;


public class WithdrawResponse implements Serializable {
    private Map<Currency,Integer> notes;
    private BankAccount account;
    private String message;

    public WithdrawResponse(Map<Currency, Integer> notesDispensed, BankAccount account ) {
        this.account = account;
        this.notes = notesDispensed;
    }

    public long getTotal(){
        if(!notes.isEmpty()){
            LongAdder adder = new LongAdder();
            notes.entrySet().stream().forEach(e -> adder.add(e.getKey().getAmount() * e.getValue()));
            return adder.sum();
        }else{
            return 0;
        }
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
