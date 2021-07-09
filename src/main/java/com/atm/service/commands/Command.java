package com.atm.service.commands;

import com.atm.service.ServiceException;

public interface Command {
    void call() throws ServiceException;
    void rollback() throws ServiceException;
}
