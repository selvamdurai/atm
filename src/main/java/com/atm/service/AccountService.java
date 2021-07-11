package com.atm.service;

import com.atm.exception.ServiceException;
import com.atm.model.*;
import com.atm.repo.AccountRepository;
import com.atm.repo.AtmRepository;
import com.atm.service.commands.AccountCommand;
import com.atm.service.commands.AtmCommand;
import com.atm.service.commands.Command;
import com.atm.service.validation.ATMValidator;
import com.atm.service.validation.AccountValidator;
import com.atm.service.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AtmRepository atmRepository;
    private Logger LOGGER = Logger.getLogger(AccountService.class.getName());



    @Autowired
    public AccountService(AccountRepository accountRepository, AtmRepository atmRepository) {
        this.accountRepository = accountRepository;
        this.atmRepository = atmRepository;
    }

    public AccountBalance getBalance(String accountNumber) {
        AccountBalance ab;
        if( null != this.accountRepository.getAccounts().get(accountNumber)){
            BigDecimal balance = this.accountRepository.getAccounts().get(accountNumber).getBalance().setScale(2);
            ab = new AccountBalance(accountNumber,balance.longValue(), "SUCCESS");
        }else{
            ab = new AccountBalance();
            ab.setMessage("Invalid Account number provided.");
        }
        return ab;
    }

    /***
     *
     * 1. Validate
     *     a. Check if the withdrawAmount is less than ATM cash balance
     *     b. Check if the amount is a multiple of lowest currency available (amount % 5 == 0)
     *     c. Check if the amount is less than available balance for the account including overdraft
     * 2. Allow withdraw - update account Balance and update ATM cash
     *     a. Calculate and return denomination for the amount - if false - return error message
     *     b. onFailure rollback.
     *
     *
     * @param accountNumber
     * @param withdrawAmount
     * @return
     * @throws ServiceException
     **/
    public WithdrawResponse withDrawAmount(String accountNumber, BigDecimal withdrawAmount){
        Map<Currency, Integer> notes = new HashMap<>();
        String message="SUCCESS";
        BankAccount account = this.accountRepository.getAccounts().get(accountNumber);
        AtmCashRegister atmCashRegister = atmRepository.getCashRegister();
        try{
            if(null != account){
                Validator atmValidator = new ATMValidator(atmCashRegister, withdrawAmount);
                Validator accountValidator = new AccountValidator(account,withdrawAmount);
                List<Validator> validators = List.of(atmValidator,accountValidator);
                for(Validator validator : validators){
                    if(!validator.validate()){
                        message = validator.getValidationErrorMessage();
                        throw new ServiceException("Validation Error: "+ message);
                    }
                }
                List<Command> commands = new ArrayList<>();
                try{
                    //update Account Balance
                    Command accountCommand = new AccountCommand(account,withdrawAmount);
                    accountCommand.call();
                    account = ((AccountCommand)accountCommand).getBankAccount();
                    commands.add(accountCommand);
                    accountRepository.save(account);
                    //update ATMCashRegister
                    Command atmCommand = new AtmCommand(atmCashRegister, withdrawAmount);
                    atmCommand.call();
                    notes =   ((AtmCommand)atmCommand).getNotes();
                    commands.add(atmCommand);

                }catch(Exception ex){
                    //rollback commands
                    commands.forEach((c) -> {
                        try {
                            c.rollback();
                        } catch (ServiceException e) {
                            //e.printStackTrace();
                            LOGGER.log(Level.WARNING, "Exception occurred during rollback operation. Master reset advised.", e);
                        }
                    });
                    message = ex.getMessage();
                }
            }else{
                message = "Invalid Account number provided.";
            }


        }catch(ServiceException exp){
            message = exp.getMessage();
            LOGGER.log(Level.WARNING, message, exp);
        }
        WithdrawResponse response = new WithdrawResponse(notes, account);
        response.setMessage(message);
        return response;
    }

    public DepositResponse deposit(AccountBalance accountBalance) {
        BankAccount account = this.accountRepository.getAccounts().get(accountBalance.getAccountNumber());
        String message;
        if(null != account){
            account.deposit(new BigDecimal(accountBalance.getBalance()));
            message = "SUCCESS";
        }else{
            message = "Invalid Account number provided.";
        }
        return new DepositResponse(message);
    }
}
