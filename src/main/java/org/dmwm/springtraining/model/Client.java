package org.dmwm.springtraining.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Client {

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Account> accounts;

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    private String name;

    @Column(name = "bank_id")
    private long bankId;

    @ManyToOne
    @JoinColumn(name = "bank_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Bank bank;

    public void addAccount(Account account) {
        account.setClient(this);
        account.setClientId(this.getClientId());
    }

}
