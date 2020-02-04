package org.dmwm.springtraining.data;

import org.dmwm.springtraining.model.Bank;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource
public interface BankRepository extends PagingAndSortingRepository<Bank, Long> {
}
