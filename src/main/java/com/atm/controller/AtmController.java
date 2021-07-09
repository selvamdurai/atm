package com.atm.controller;

import com.atm.model.AtmCashRegister;
import com.atm.service.ATMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
