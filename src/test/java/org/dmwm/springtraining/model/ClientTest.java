package org.dmwm.springtraining.model;

import lombok.extern.java.Log;
import org.dmwm.springtraining.data.AccountRepository;
import org.dmwm.springtraining.data.BankRepository;
import org.dmwm.springtraining.data.ClientRepository;
import org.dmwm.springtraining.testutil.Generator;
import org.dmwm.springtraining.testutil.Printer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankRepository bankRepository;

    @Test
    public void bank_linked_entity() {
        Bank bank = Generator.newBank();
        bankRepository.save(bank);

        for(int i = 0; i < 3; i++) {
            Client client = Generator.newClient();
            bank.addClient(client);
            clientRepository.save(client);


            Account account = Generator.newAccount(client);
            accountRepository.save(account);
        }

        Printer.pretty("BANK INFO", bankRepository.findAll());

        bankRepository.findAll().forEach(b -> {
            System.out.println("ASSERT BANK = " + b.toString());
            assertThat(b.getClients(), hasSize(greaterThan(0)));
        });

    }

    @Test
    public void client_linked_entity() {

        Bank bank = Generator.newBank();
        bankRepository.save(bank);

        Client c = Generator.newClient();
        bank.addClient(c);

        clientRepository.save(c);
        System.out.println("CLIENT = " + c);

        Account a = Generator.newAccount(c);
        accountRepository.save(a);
        System.out.println("ACCOUNT = " + a);

        Printer.pretty("ALL CLIENTS", clientRepository.findAll());
        Printer.pretty("ALL ACCOUNTS", accountRepository.findAll());

        clientRepository.findAll().forEach(client ->
                assertThat(client.getAccounts().get(0).getAccountNumber(),
                        equalTo(a.getAccountNumber()))
        );

    }


    @Test
    public void client_multiple_linked_accs() {
        Bank bank = Generator.newBank();
        bankRepository.save(bank);

        Client c = Generator.newClient();
        bank.addClient(c);
        clientRepository.save(c);

        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            accounts.add(Generator.newAccount());
            c.addAccount(accounts.get(i));
        }


        accounts.forEach(account -> {
            System.out.println("Saving " + account.getAccountNumber() + "...");
            accountRepository.save(account);
        });

        System.out.println("ALL CLIENTS = " + clientRepository.findAll().toString());

    }

}
