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

    public AtmCashRegister getAtmCashStatus() {
        return atmRepository.getCashRegister();
    }

    public String loadCash(Map<Currency, Integer> notes) {
        String message = "SUCCESS";
        atmRepository.getCashRegister().addNotes(notes);
        return message;
    }
}
