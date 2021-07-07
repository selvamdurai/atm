package com.atm.repo;

import com.atm.model.BankAccount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AccountConfig {

        @Bean
        CommandLineRunner commandLineRunner(AccountRepository repo){
            return args -> {
                BankAccount account1 = new BankAccount(
                        "123456789",
                        1234,
                        800.0, 200);
                BankAccount account2 = new BankAccount(
                        "987654321",
                        4321,
                        1230.0, 150);
                Map<String, BankAccount> accounts = new HashMap<>();
                accounts.put(account1.getAccountNumber(), account1);
                accounts.put(account2.getAccountNumber(), account2);
                repo.saveAll(accounts);
            };
        }

}
