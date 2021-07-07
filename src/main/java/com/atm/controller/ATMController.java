package com.atm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.atm.service.ATMService;


@RestController
@RequestMapping("/atm")
public class ATMController {
    private final ATMService ATMService;

    public ATMController(ATMService ATMService) {
        this.ATMService = ATMService;
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> getBalance(@PathVariable("accountNumber") String account){
        Double balance = ATMService.getBalance(account);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getAllEmployeeById(@PathVariable("id") Long id){
        Employee employee = ATMService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


}
