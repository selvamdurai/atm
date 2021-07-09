package com.atm.service.validation;

public interface Validator {
    boolean validate();
    String getValidationErrorMessage();
}
