package com.atm.service;

import com.atm.model.AtmCashRegister;
import com.atm.model.Currency;
import com.atm.repo.AtmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;


@Service
public class ATMService {
    private final AtmRepository atmRepository;

    @Autowired
    public ATMService(AtmRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public Map<Currency, Integer> withdrawCash(BigDecimal amount) throws ServiceException{
        Map<Currency , Integer> notesMap;
        AtmCashRegister cashRegister = atmRepository.getCashRegister();
        notesMap = cashRegister.withdrawCash(amount);
        return notesMap;
    }

    public void addNotes(Map<Currency, Integer> notes) {

    }
}
