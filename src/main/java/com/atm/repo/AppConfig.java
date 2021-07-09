package com.atm.repo;

import com.atm.model.AtmCashRegister;
import com.atm.model.BankAccount;
import com.atm.model.Currency;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

        @Bean
        CommandLineRunner commandLineRunner(AccountRepository accountRepo,
                                            AtmRepository atmRepository){
            return args -> {
                BankAccount account1 = new BankAccount(
                        "123456789",
                        new BigDecimal(800.0), new BigDecimal(200));
                BankAccount account2 = new BankAccount(
                        "987654321",
                        new BigDecimal(1230.0), new BigDecimal(150));
                Map<String, BankAccount> accounts = new HashMap<>();
                accounts.put(account1.getAccountNumber(), account1);
                accounts.put(account2.getAccountNumber(), account2);
                accountRepo.saveAll(accounts);

                Map<Currency, Integer> currencies = new HashMap<>();
                currencies.put(Currency.FIFTIES,10);
                currencies.put(Currency.TWENTIES,30);
                currencies.put(Currency.TENS,30);
                currencies.put(Currency.FIVES,20);
                AtmCashRegister cashRegister = new AtmCashRegister(currencies);
                atmRepository.save(cashRegister);
            };
        }

}
