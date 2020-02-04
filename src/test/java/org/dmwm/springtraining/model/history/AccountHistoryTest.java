package org.dmwm.springtraining.model.history;

import org.dmwm.springtraining.data.AccountRepository;
import org.dmwm.springtraining.data.BankRepository;
import org.dmwm.springtraining.data.ClientRepository;
import org.dmwm.springtraining.data.history.AccountHistoryRepository;
import org.dmwm.springtraining.model.Account;
import org.dmwm.springtraining.model.Bank;
import org.dmwm.springtraining.model.Client;
import org.dmwm.springtraining.testutil.Generator;
import org.dmwm.springtraining.testutil.Printer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountHistoryTest {

    @Autowired
    AccountHistoryRepository historyRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void save_account_history() {

        Bank bank = Generator.newBank();
        bankRepository.save(bank);

        Client c1 = Generator.newClient();
        bank.addClient(c1);
        clientRepository.save(c1);

        Client c2 = Generator.newClient();
        bank.addClient(c2);
        clientRepository.save(c2);

        Account a1 = Generator.newAccount(c1);
        Account a2 = Generator.newAccount(c2);

        accountRepository.save(a1);
        accountRepository.save(a2);

        Printer.pretty("BANK INFO", bankRepository.findAll());

        AccountHistory history = new AccountHistory();

        history.setAccountNumber(a1.getAccountNumber());
        history.setAccountNumber(a2.getAccountNumber());
        history.setBalanceAfter(123);
        history.setBalanceBefore(321);
        history.setOperation(AccountOperation.WITHDRAW);
        history.setTime(LocalDateTime.now());

        historyRepository.save(history);

        System.out.println("HISTORY RECORD = " + history.toString());

    }
}
