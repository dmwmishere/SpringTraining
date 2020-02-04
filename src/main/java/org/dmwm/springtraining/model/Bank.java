package org.dmwm.springtraining.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dmwm.springtraining.exception.ClientExistsException;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bank {

    @Id
    @Column(name = "bank_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bankId;

    private String bankName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bank", cascade = CascadeType.REMOVE)
    private List<Client> clients;

    public void addClient(Client client) {
        if (clients != null && clients.stream().anyMatch(c -> c.getName().equals(client.getName()))) {
            throw new ClientExistsException(client);
        }
        client.setBank(this);
        client.setBankId(this.getBankId());
    }

}
