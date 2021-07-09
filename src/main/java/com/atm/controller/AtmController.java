package com.atm.controller;

import com.atm.model.AtmCashRegister;
import com.atm.model.Currency;
import com.atm.service.ATMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/atm")
public class AtmController {
    private ATMService atmService;

    public AtmController(ATMService atmService) {
        this.atmService = atmService;
    }

    @GetMapping
    public ResponseEntity<AtmCashRegister> getAtmCashStatus(){
        return new ResponseEntity<>(atmService.getAtmCashStatus(), HttpStatus.OK) ;
    }

    @PutMapping("/load")
    public ResponseEntity<String> load(@RequestBody Map<Currency,Integer> cashInput){
        String message = atmService.loadCash(cashInput);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
