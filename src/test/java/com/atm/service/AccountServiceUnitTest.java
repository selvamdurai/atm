package com.atm.service;

import com.atm.exception.ServiceException;
import com.atm.model.AccountBalance;
import com.atm.model.AtmCashRegister;
import com.atm.model.BankAccount;
import com.atm.model.Currency;
import com.atm.repo.AccountRepository;
import com.atm.repo.AtmRepository;
import com.atm.service.commands.AtmCommand;
import com.atm.service.commands.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceUnitTest {

    private AccountRepository accountRepository;
    private AtmRepository atmRepository;
    private AccountService accountService;

    @Mock
    AtmCashRegister atmCashRegister;

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
    }

    @Test
    void getCorrectBalanceForTheGivenAccount() {
        assertEquals(accountService.getBalance("123456789").getBalance(), 800);
        assertEquals(accountService.getBalance("987654321").getBalance(), 1230);
        assertNotEquals(accountService.getBalance("987654321").getBalance(), 1800);
    }

    @Test
    void checkInvalidAccountNumber() {
        assertEquals(accountService.getBalance("532343423").getMessage(), "Invalid Account number provided.");

    }


    @Test
    void checkWithdrawActionReturnsCorrectAmount(){
        assertEquals(accountService.withDrawAmount("123456789", new BigDecimal(250)).getTotal(), 250);
        assertNotEquals(accountService.withDrawAmount("123456789", new BigDecimal(250)).getTotal(), 200);
    }

    @Test
    void checkOverdraftLimit(){
        accountService.withDrawAmount("123456789", new BigDecimal(800));
        assertEquals(accountService.getBalance("123456789").getBalance(), 0);
        assertEquals(accountService.withDrawAmount("123456789",
                new BigDecimal(150)).getTotal(), 150);
    }

    @Test
    void checkIfCorrectBalanceIsReturnedAfterDeposit(){
        assertEquals(accountService.getBalance("123456789").getBalance(), 800);
        accountService.deposit(new AccountBalance("123456789", 500L));
        assertEquals(accountService.getBalance("123456789").getBalance(), 1300);

    }

    @Test
    void checkInsufficientBalanceCondition(){
        accountService.withDrawAmount("123456789", new BigDecimal(800));
        accountService.withDrawAmount("123456789", new BigDecimal(200));
        assertEquals(accountService.getBalance("123456789").getBalance(), -200);
        assertEquals(accountService.withDrawAmount("123456789", new BigDecimal(250)).getTotal(), 0);
    }

    @Test
    void checkInsufficientBalanceConditionMessage(){
        accountService.withDrawAmount("123456789", new BigDecimal(800));
        accountService.withDrawAmount("123456789", new BigDecimal(200));
        assertEquals(accountService.getBalance("123456789").getBalance(), -200);
        assertEquals(accountService.withDrawAmount("123456789", new BigDecimal(250)).getMessage(),
                "Validation Error: Withdrawal amount greater than available account balance. Please enter correct amount.");
    }

    @Test
    void checkBalanceIsCorrectAfterWithDrawAmount() {
        accountService.withDrawAmount("123456789", new BigDecimal(600));
        assertEquals(accountService.getBalance("123456789").getBalance(), 200);
        accountService.withDrawAmount("123456789", new BigDecimal(400));
        assertEquals(accountService.getBalance("123456789").getBalance(), -200);
    }

    @Test
    void checkATMCashLimit() {
        accountService.withDrawAmount("123456789", new BigDecimal(1000));
        accountService.withDrawAmount("987654321", new BigDecimal(500));
        assertEquals(accountService.getBalance("123456789").getBalance(), -200);
        assertEquals(accountService.getBalance("987654321").getBalance(), 730);
        assertEquals(accountService.withDrawAmount("987654321", new BigDecimal(250)).getMessage(),
                "Validation Error: Cash unavailable.!!");
    }


    @Test
    void deposit() {
    }
}