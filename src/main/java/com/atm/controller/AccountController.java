package com.atm.controller;

import com.atm.exception.ApplicationException;
import com.atm.exception.ServiceException;
import com.atm.model.AccountBalance;
import com.atm.model.DepositResponse;
import com.atm.model.WithdrawRequest;
import com.atm.model.WithdrawResponse;
import com.atm.service.AccountService;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private static final Logger LOGGER = Logger.getLogger(AccountController.class.getName());

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path="/balance/{account}")
    public ResponseEntity<AccountBalance> getBalance(@PathVariable("account") String accountNumber){
        authorize(accountNumber);
        try{
            return new ResponseEntity<>(accountService.getBalance(accountNumber),
                                HttpStatus.OK) ;
        }catch (RuntimeException ex){
            LOGGER.log(Level.SEVERE, "Could not get Account balance.", ex);
            throw new ApplicationException("Could not get Account balance.");
        }
    }

    @PutMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@RequestBody WithdrawRequest request){
        authorize(request.getAccount());
        try{
            WithdrawResponse response = accountService.withDrawAmount(
                    request.getAccount(),
                    new BigDecimal(request.getAmount()));
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (RuntimeException ex){
            LOGGER.log(Level.SEVERE, "Could not withdraw amount.", ex);
            throw new ApplicationException("Could not withdraw amount.");
        }
    }

    @PutMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(@RequestBody AccountBalance accountBalance){
        authorize(accountBalance.getAccountNumber());
        try{
            DepositResponse response = accountService.deposit(accountBalance);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (RuntimeException ex){
            LOGGER.log(Level.SEVERE, "Could not deposit amount.", ex);
            throw new ApplicationException("Could not deposit amount.");
        }

    }
    public void authorize(String accountNumber) throws ApplicationException{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        if(!username.equals(accountNumber)){
            LOGGER.log(Level.SEVERE, "User is not authorized for this account.");
            throw new ApplicationException("User is not authorized for this account.");
        }
    }
}
