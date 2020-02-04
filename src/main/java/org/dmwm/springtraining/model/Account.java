package org.dmwm.springtraining.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.dmwm.springtraining.exception.NotEnoughFundsException;

import javax.persistence.*;

@Data
@Entity
public class Account implements AccountOperation {

    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @ManyToOne
    @ToString.Exclude
    private Client client;

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    @Column(name = "account_number")
    private String accountNumber;

    private double balance;

    @Column(name = "client_id")
    private long clientId;

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount < 0) {
            throw new NotEnoughFundsException(this);
        }
        balance -= amount;
    }
}
