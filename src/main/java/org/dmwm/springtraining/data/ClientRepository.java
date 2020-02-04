package org.dmwm.springtraining.data;


import org.dmwm.springtraining.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findByNameAndBankId(String clientName, long bankId);

    List<Client> findByNameContaining(String partialName);

    Client findByName(String name);

}
