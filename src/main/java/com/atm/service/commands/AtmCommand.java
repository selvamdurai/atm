package com.atm.service.commands;

import com.atm.model.AtmCashRegister;
import com.atm.model.Currency;
import com.atm.service.ATMService;
import com.atm.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;

public class AtmCommand implements Command{
    private AtmCashRegister atmCashRegister;
    private BigDecimal withdrawAmount;
    private Map<Currency, Integer> notes;
    @Autowired
    private ATMService atmService;

    public AtmCommand(AtmCashRegister atmCashRegister, BigDecimal withdrawAmount) {
        this.atmCashRegister = atmCashRegister;
        this.withdrawAmount = withdrawAmount;
    }

    @Override
    public void call() throws ServiceException {
        this.notes = atmCashRegister.withdrawCash(this.withdrawAmount);
    }

    @Override
    public void rollback() throws ServiceException{
        atmCashRegister.addNotes(notes);
    }

    public Map<Currency, Integer> getNotes() {
        return notes;
    }
}
