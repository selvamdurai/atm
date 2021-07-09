package com.atm.controller;

import com.atm.model.WithdrawRequest;
import com.atm.model.WithdrawResponse;
import com.atm.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path="/balance/{account}")
    public ResponseEntity<String> getBalance(@PathVariable("account") String accountNumber){
        return new ResponseEntity<>(accountService.getBalance(accountNumber).toString(), HttpStatus.OK) ;
    }

    @PutMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@RequestBody WithdrawRequest request){
        WithdrawResponse response = accountService.withDrawAmount(request.getAccount(),
                new BigDecimal(request.getAmount()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
