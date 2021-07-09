package com.atm.service;

import com.atm.model.AtmCashRegister;
import com.atm.model.BankAccount;
import com.atm.model.Currency;
import com.atm.model.WithdrawResponse;
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
    private String errorMessage;
    private Logger LOGGER = Logger.getLogger(AccountService.class.getName());


    @Autowired
    public AccountService(AccountRepository accountRepository, AtmRepository atmRepository) {
        this.accountRepository = accountRepository;
        this.atmRepository = atmRepository;
    }

    public BigDecimal getBalance(String accountNumber){
        //if(authorized())
        return this.accountRepository.getAccounts().get(accountNumber).getBalance();
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

    /***
     * 1. Check if authenticated and has valid token -- must have already passed prior
     * 2. Validate
     *     a. Check if the withdrawAmount is less than ATM cash balance
     *     b. Check if the amount is a multiple of lowest currency available (amount % 5 == 0)
     *     c. Check if the amount is less than available balance for the account including overdraft
     * 3. Allow withdraw - update account Balance and update ATM cash
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
                            e.printStackTrace();
                            LOGGER.log(Level.SEVERE, "Exception occurred during rollback operation. Master reset advised.", e);
                        }
                    });
                    message = "Error Occurred: " + ex.getMessage();
                }
            }else{
                message = "Invalid Account number provided.";
            }


        }catch(Exception exp){
            message = "Exception Occurred: "+ exp.getMessage();
            LOGGER.log(Level.SEVERE, message, exp);
        }
        WithdrawResponse response = new WithdrawResponse(notes, account);
        response.setMessage(message);
        return response;
    }
}
