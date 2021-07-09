package com.atm.service.validation;

import com.atm.model.AtmCashRegister;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ATMValidator implements Validator {

    private AtmCashRegister atmCashRegister;
    private BigDecimal withdrawAmount;
    private String errorMessage;


    @Autowired
    public ATMValidator(AtmCashRegister atmCashRegister, BigDecimal amount) {
        this.atmCashRegister = atmCashRegister;
        this.withdrawAmount=amount;
    }

    public boolean validate(){
        BigDecimal totalCashAvailable = this.atmCashRegister.getTotalCashAvailable();
        if(totalCashAvailable.doubleValue() == 0){
            this.errorMessage="Cash unavailable.!!";
            return false;
        }
        if(withdrawAmount.doubleValue() > totalCashAvailable.doubleValue()){
            this.errorMessage="Cash cannot be dispensed. Please enter different amount.";
            return false;
        }
        return validateAmount(withdrawAmount);
    }

    public boolean validateAmount(BigDecimal withdrawAmount){
        int lowest = this.atmCashRegister.getLowestCurrencyNoteAvailable();
        if(withdrawAmount.intValue() % lowest != 0){
            this.errorMessage = "Amount invalid. Please re-enter correct amount.";
            return false;
        }
        if(!atmCashRegister.isDispensable(withdrawAmount)){
            this.errorMessage = "Cannot dispense this amount. Please re-enter different amount.";
            return false;
        }

        return true;
    }

    @Override
    public String getValidationErrorMessage() {
        return errorMessage;
    }

}
