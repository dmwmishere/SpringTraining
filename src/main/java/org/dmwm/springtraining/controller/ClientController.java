package org.dmwm.springtraining.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.dmwm.springtraining.data.AccountRepository;
import org.dmwm.springtraining.data.ClientRepository;
import org.dmwm.springtraining.model.Account;
import org.dmwm.springtraining.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("{id}/clients")
@Api(tags = "Контроллер по клиентам")
@Log
public class ClientController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @ApiOperation(value = "Удаление клиента по имени")
    @RequestMapping(value = "removeClientByName", method = RequestMethod.DELETE)
    public void removeClientByName(String clientName){
        clientRepository.delete(clientRepository.findByName(clientName).getClientId());
    }

    @ApiOperation(value = "Поиск по имени (частичное совпадение)")
    @RequestMapping(value = "findByPartName", method = RequestMethod.GET)
    public List<Client> findByPartName(String partialName){
        return clientRepository.findByNameContaining(partialName);
    }


    @ApiOperation(value = "Список счетов клиентов")
    @RequestMapping(value = "getClientAccounts", method = RequestMethod.GET)
    public List<Account> getClientAccounts(String clientName){
        return clientRepository.findByName(clientName).getAccounts();
    }

    @ApiOperation(value = "Создать новый счет у клиента")
    @RequestMapping(value = "addAccount", method = RequestMethod.POST)
    public void addAccount(@PathVariable("id") long clientId){
        Client client = clientRepository.findOne(clientId);
        Account account = new Account();

        account.setAccountNumber(UUID.randomUUID().toString().replace("-", ""));

        client.addAccount(account);
        accountRepository.save(account);
        clientRepository.save(client);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource repositoryLinksResource) {
        return repositoryLinksResource;
    }

}
