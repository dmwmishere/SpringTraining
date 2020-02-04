package org.dmwm.springtraining.data;

import org.dmwm.springtraining.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);

}
