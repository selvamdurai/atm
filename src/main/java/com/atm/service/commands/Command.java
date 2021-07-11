package com.atm.service.commands;

import com.atm.exception.ServiceException;

public interface Command {
    void call() throws ServiceException;
    void rollback() throws ServiceException;
}
