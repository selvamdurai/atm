package com.atm.model;

public class DepositResponse {
    private String message;

    public DepositResponse(String message) {
        this .message=message;
    }

    public String getMessage() {
        return message;
    }
}
