package org.dmwm.springtraining.exception;

import org.dmwm.springtraining.model.Client;

public class ClientExistsException extends BankException {

    public ClientExistsException(Client client) {
        super(client.getBank(), "client with name " + client.getName() + " exists");
    }
}
