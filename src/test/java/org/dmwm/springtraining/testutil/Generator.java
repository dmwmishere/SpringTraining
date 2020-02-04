package org.dmwm.springtraining.testutil;

import com.github.javafaker.Faker;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.dmwm.springtraining.model.Account;
import org.dmwm.springtraining.model.Bank;
import org.dmwm.springtraining.model.Client;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

@UtilityClass
public class Generator {

    private Faker faker = new Faker(new Locale("ru"));

    public Bank newBank(){
        Bank bank = new Bank();
        bank.setBankName(faker.company().name());
        return bank;
    }

    public Client newClientAtBank() {
        Client client = newClient();

        Bank bank = newBank();

        client.setBank(bank);
        client.setBankId(bank.getBankId());
        return client;
    }

    public Client newClient() {
        return newClient(faker.name().fullName(), null);
    }

    public Client newClient(@NonNull String name, Account... accounts) {
        Client client = new Client();
        client.setName(name);
        if (accounts != null) {
            client.setAccounts(Arrays.asList(accounts));
        }
        return client;
    }

    public Account newAccount() {
        return newAccount(null);
    }

    public Account newAccount(Client fromClient) {
        return newAccount(fromClient, 1000);
    }

    public Account newAccount(Client fromClient, double initialBalance) {
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString().replace("-", ""));
        if (fromClient != null) {
            account.setClientId(fromClient.getClientId());
            account.setClient(fromClient);
        }
        account.setBalance(initialBalance);
        return account;
    }

}
