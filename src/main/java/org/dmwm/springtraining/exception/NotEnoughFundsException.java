package org.dmwm.springtraining.exception;

import org.dmwm.springtraining.model.Account;

public class NotEnoughFundsException extends BankException {

    public NotEnoughFundsException(Account account) {
        super(account.getClient().getBank(), "account " + account.getAccountNumber() + " balance: " + account.getBalance());
    }
}
