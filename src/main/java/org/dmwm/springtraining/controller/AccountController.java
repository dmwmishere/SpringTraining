package org.dmwm.springtraining.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.dmwm.springtraining.data.AccountRepository;
import org.dmwm.springtraining.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("{id}/accounts")
@Api(tags = "Контроллер по счетам")
@Log
public class AccountController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    AccountRepository accountRepository;

    @ApiOperation(value = "Все счета в БД")
    @RequestMapping(value = "allAccs", method = RequestMethod.POST)
    public Map<String, Double> allAccs() {
        Map<String, Double> accs = new HashMap<>();
        accountRepository.findAll().forEach(account -> accs.put(account.getAccountNumber(), account.getBalance()));
        return accs;
    }

    @ApiOperation(value = "Снятие со счета")
    @RequestMapping(value = "withdraw", method = RequestMethod.POST)
    public void withdraw(@RequestParam String accountNumber, @RequestParam double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            throw new IllegalArgumentException("Account not found!");
        }
        accountRepository.save(account);
    }

    @ApiOperation(value = "Пополнение счета")
    @RequestMapping(value = "deposit", method = RequestMethod.POST)
    public void deposit(@RequestParam String accountNumber, @RequestParam double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            throw new IllegalArgumentException("Account not found!");
        }
        accountRepository.save(account);
    }

    @ApiOperation(value = "Перевод со счета на счет")
    @RequestMapping(value = "transfer", method = RequestMethod.POST)
    public void transfer(@RequestParam String fromAccount, @RequestParam String toAccount, @RequestParam double amount) {
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
    }


    @Override
    public RepositoryLinksResource process(RepositoryLinksResource repositoryLinksResource) {
        return repositoryLinksResource;
    }

}
