package com.paul.bankapi.Account.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Transaction.Model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String pin;
    private Integer balance;


    @OneToOne(targetEntity = Customer.class)
    @JsonIgnore
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private List<Transaction> transactions;

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }
}
