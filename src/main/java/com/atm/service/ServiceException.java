package com.atm.service;

public class ServiceException extends Exception{
    public ServiceException(String errMessage) {
        super(errMessage);
    }

    public ServiceException(String errMessage, Throwable err){
        super(errMessage,err);
    }
}
