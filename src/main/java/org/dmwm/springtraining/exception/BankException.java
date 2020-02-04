package org.dmwm.springtraining.exception;

import org.dmwm.springtraining.model.Bank;

public abstract class BankException extends RuntimeException {

    public BankException(Bank bank, String msg) {
        super("Exception at bank " + bank.getBankName() + " - " + msg);
    }

}
