package com.atm.repo;

import com.atm.model.AtmCashRegister;
import org.springframework.stereotype.Component;

@Component
public class AtmRepository {
    private AtmCashRegister cashRegister;

    public AtmRepository(AtmCashRegister cashRegister) {
        this.cashRegister = cashRegister;
    }

    public AtmCashRegister getCashRegister() {
        return cashRegister;
    }

    public void save(AtmCashRegister cashRegister) {
        this.cashRegister = cashRegister;
    }
}
