package org.dmwm.springtraining.model;

import lombok.extern.java.Log;
import org.dmwm.springtraining.data.AccountRepository;
import org.dmwm.springtraining.data.BankRepository;
import org.dmwm.springtraining.data.ClientRepository;
import org.dmwm.springtraining.exception.ClientExistsException;
import org.dmwm.springtraining.testutil.Generator;
import org.dmwm.springtraining.testutil.Printer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BankTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankRepository bankRepository;

    @Test(expected = ClientExistsException.class)
    public void duplicate_client_exception() {
        Bank bank = Generator.newBank();
        bankRepository.save(bank);

        Client client = Generator.newClient();
        bank.addClient(client);

        clientRepository.save(client);

        Printer.pretty("BANK", bankRepository.findAll());
        bankRepository.findOne(bank.getBankId()).addClient(client);
    }
}
