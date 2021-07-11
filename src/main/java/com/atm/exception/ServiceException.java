package com.atm.exception;

public class ServiceException extends Exception{
    public ServiceException(String errMessage) {
        super(errMessage);
    }

    public ServiceException(String errMessage, Throwable err){
        super(errMessage,err);
    }

}
