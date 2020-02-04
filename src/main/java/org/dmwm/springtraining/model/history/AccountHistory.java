package org.dmwm.springtraining.model.history;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.dmwm.springtraining.model.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long historyId;

    @Column(name = "accountNumber")
    private String accountNumber;

    @Column(name = "fromAccountNumber")
    private String fromAccountNumber;

    private AccountOperation operation;

    private LocalDateTime time;

    private double balanceBefore;

    private double balanceAfter;

}
