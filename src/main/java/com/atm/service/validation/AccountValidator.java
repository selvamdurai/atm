package com.atm.service.validation;

import com.atm.model.BankAccount;


import java.math.BigDecimal;

public class AccountValidator implements Validator {
    private BankAccount account;
    private BigDecimal withdrawAmount;
    private String errorMessage;

    public AccountValidator(BankAccount account, BigDecimal amount) {
        this.account = account;
        this.withdrawAmount=amount;
    }

    public boolean validate(){
        double balance = account.getBalance().doubleValue();
        double overDraftLimit = account.getOverdraftLimit().doubleValue();
        if ((balance + overDraftLimit ) >= withdrawAmount.doubleValue() ){
            return true;
        }else{
            errorMessage="Withdrawal amount greater than available account balance. Please enter correct amount.";
            return false;
        }
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    @Override
    public String getValidationErrorMessage() {
        return errorMessage;
    }
}
