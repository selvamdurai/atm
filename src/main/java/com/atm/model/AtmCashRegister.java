package com.atm.model;

import com.atm.exception.ServiceException;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class AtmCashRegister {
    private volatile Map<Currency,Integer> cashRegister;

    public Map<Currency, Integer> getCashRegister() {
        return cashRegister;
    }

    public AtmCashRegister() {
    }

    public AtmCashRegister(Map<Currency, Integer> cashRegister) {
        this.cashRegister = cashRegister;
    }

    public Map<Currency, Integer> withdrawCash(BigDecimal withdrawAmount) throws ServiceException {
        Map<Currency , Integer> notesMap = new HashMap<>();
        try{
            double amount = withdrawAmount.doubleValue();
            for(int i=1; i<=4; i++){
                Currency note = Currency.getCurrency(i);
                int numberofNotesAvailable = cashRegister.get(note);
                if(numberofNotesAvailable == 0){
                    continue;
                }
                int numberofNotesRequired = (int) amount / note.getAmount();
                if(numberofNotesAvailable >= numberofNotesRequired){
                    notesMap.put(note, numberofNotesRequired);
                    cashRegister.put(note, numberofNotesAvailable - numberofNotesRequired);
                    amount = (int) amount % note.getAmount();
                }else if(numberofNotesAvailable < numberofNotesRequired){
                    notesMap.put(note, numberofNotesAvailable);
                    cashRegister.put(note, 0);
                    amount -= numberofNotesAvailable * note.getAmount();
                }
                if(amount == 0){
                    break;
                }
            }
        }catch (Exception e){
            throw new ServiceException("Error occurred: " + e.getMessage(), e);
        }
        return notesMap;
    }

    public boolean isDispensable(BigDecimal withdrawAmount){
        double amount = withdrawAmount.doubleValue();
        for(int i=1; i<=4; i++){
            Currency note = Currency.getCurrency(i);
            int numberofNotesAvailable = cashRegister.get(note);
            if(numberofNotesAvailable == 0){
                continue;
            }
            int numberofNotesRequired = (int) amount / note.getAmount();
            if(numberofNotesAvailable >= numberofNotesRequired){
                amount = (int) amount % note.getAmount();
            }else if(numberofNotesAvailable < numberofNotesRequired){
                amount -= numberofNotesAvailable*note.getAmount();
            }
        }
        return !(amount > 0);
    }

    public BigDecimal getTotalCashAvailable(){
        return
                new BigDecimal(
                        cashRegister.get(Currency.FIFTIES)*50 +
                                cashRegister.get(Currency.TWENTIES)*20 +
                                cashRegister.get(Currency.TENS)*10 +
                                cashRegister.get(Currency.FIVES)*5 ).setScale(2);
    }

    public void addNotes(Map<Currency, Integer> notes) {
        notes.forEach((note, number) -> cashRegister.put(note, cashRegister.get(note) + number));
    }

    public boolean decrement(Currency currency, int count){
        int currentCount =  cashRegister.get(currency);
        if(currentCount >= count){
            cashRegister.put(currency,currentCount-count);
            return true;
        }else{
            return false;
        }
    }

    public void increment(Currency currency, int count){
        int currentCount =  cashRegister.get(currency);
        cashRegister.put(currency,currentCount+count);
    }

    public int getLowestCurrencyNoteAvailable(){
        int lowest=0;
        if(cashRegister.get(Currency.FIVES) >0)
            lowest=5;
        else if(cashRegister.get(Currency.TENS)>0)
            lowest=10;
        else if(cashRegister.get(Currency.TWENTIES)>0)
            lowest=20;
        else if(cashRegister.get(Currency.FIFTIES)>0)
            lowest=50;
        return lowest;
    }


    @Override
    public String toString() {
        return "AtmCashRegister{" +
                "fifties=" + cashRegister.get(Currency.FIFTIES) +
                ", twenties=" + cashRegister.get(Currency.TWENTIES) +
                ", tens=" + cashRegister.get(Currency.TENS) +
                ", fives=" + cashRegister.get(Currency.FIVES) +
                '}';
    }

}
