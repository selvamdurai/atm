package com.atm.service;

import com.atm.model.AtmCashRegister;
import com.atm.model.BankAccount;
import com.atm.model.Currency;
import com.atm.repo.AccountRepository;
import com.atm.repo.AtmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ATMServiceUnitTest {

    private AccountRepository accountRepository;
    private AtmRepository atmRepository;
    private AccountService accountService;
    private ATMService atmService;

    @BeforeEach
    void setUp() {
        BankAccount account1 = new BankAccount("123456789", new BigDecimal(800), new BigDecimal(200));
        BankAccount account2 = new BankAccount("987654321", new BigDecimal(1230), new BigDecimal(150));
        Map<String, BankAccount> accounts = new HashMap<>();
        accounts.put(account1.getAccountNumber(), account1);
        accounts.put(account2.getAccountNumber(),account2);
        accountRepository = Mockito.mock(AccountRepository.class);
        when(accountRepository.getAccounts()).thenReturn(accounts);
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.FIFTIES, 10);
        notes.put(Currency.TWENTIES,30);
        notes.put(Currency.TENS,30);
        notes.put(Currency.FIVES,20);
        AtmCashRegister cashRegister = new AtmCashRegister(notes);
        atmRepository = Mockito.mock(AtmRepository.class);
        when(atmRepository.getCashRegister()).thenReturn(cashRegister);
        accountService = new AccountService(accountRepository,atmRepository);
        atmService = new ATMService(atmRepository);
    }

    @Test
    void checkInitialATMStatus() {
        assertEquals(atmService.getAtmCashStatus().getTotalCashAvailable().doubleValue(), 1500);
    }
    @Test
    void checkATMCashStatusAfterWithdrawal() {
        assertEquals(atmService.getAtmCashStatus().getTotalCashAvailable().doubleValue(), 1500);
        accountService.withDrawAmount("123456789", new BigDecimal(1000));
        assertEquals(atmService.getAtmCashStatus().getTotalCashAvailable().doubleValue(), 500);
    }

    @Test
    void checkATMLoadCash() {
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.FIFTIES,50); //50x50 = 2500
        notes.put(Currency.TWENTIES,20); // 20x20 = 400
        notes.put(Currency.TENS,10);// 10 x 10 = 100
        notes.put(Currency.FIVES,5); // 5x5 =25
        //total amount = 1500 + 2500 + 400 + 100 + 25 = 4525
        atmService.loadCash(notes);
        assertEquals(atmService.getAtmCashStatus().getTotalCashAvailable().doubleValue(), 4525);

    }

}