package org.dmwm.springtraining.data.history;

import org.dmwm.springtraining.model.history.AccountHistory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface AccountHistoryRepository extends PagingAndSortingRepository<AccountHistory, Long> {

    List<AccountHistory> findByTimeBetween(LocalDateTime from, LocalDateTime to);

}
