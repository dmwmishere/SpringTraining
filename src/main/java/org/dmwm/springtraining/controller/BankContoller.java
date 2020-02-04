package org.dmwm.springtraining.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.dmwm.springtraining.data.BankRepository;
import org.dmwm.springtraining.data.ClientRepository;
import org.dmwm.springtraining.model.Bank;
import org.dmwm.springtraining.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping("{id}/banks")
@Api(tags = "Контроллер по банку")
@Log
public class BankContoller implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping(value = "findByExample", method = RequestMethod.POST)
    public Iterable<Bank> findAll() {
        return bankRepository.findAll();
    }

    @ApiOperation(value = "Все клиенты банка")
    @RequestMapping(value = "allClients", method = RequestMethod.GET)
    public Iterable<String> allClients(@PathVariable("id") long bankid) {
        return bankRepository.findOne(bankid).getClients().stream().map(Client::getName).collect(Collectors.toList());
    }

    @ApiOperation(value = "Поиск клиента по имени")
    @RequestMapping(value = "findClientByName", method = RequestMethod.GET)
    public Client findClientByName(@PathVariable("id") long bankId, String clientName) {
        return clientRepository.findByNameAndBankId(clientName, bankId);
    }

    @ApiOperation(value = "Добавление нового клента")
    @RequestMapping(value = "newClient", method = RequestMethod.POST)
    public void addClient(@PathVariable("id") long bankid, @RequestBody Client client) {
        Bank bank = bankRepository.findOne(bankid);
        log.info("Add " + client.toString() + " to " + bank.toString());
        bank.addClient(client);
        clientRepository.save(client);
        bankRepository.save(bank);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource repositoryLinksResource) {
        repositoryLinksResource.add(ControllerLinkBuilder
                .linkTo(methodOn(BankContoller.class)
                        .findAll())
                .withRel("allBanks")
        );

        return repositoryLinksResource;
    }
}
