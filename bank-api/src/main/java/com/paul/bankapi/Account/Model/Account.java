package com.paul.bankapi.Account.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Transaction.Model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ApiModel(description = "Represents a bank account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Account ID", example = "1")
    private Integer id;

    @ApiModelProperty(value = "Account name", example = "Savings Account")
    private String accountName;

    @ApiModelProperty(value = "Account balance", example = "5000")
    private Integer balance;

    @ApiModelProperty(value = "Account reference ID", example = "acc001")
    private String accountRefID;

    @OneToOne(targetEntity = Customer.class)
    @JsonIgnore
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    @ApiModelProperty(value = "List of transactions associated with the account")
    private List<Transaction> transactions;



    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", accountRefID='" + accountRefID + '\'' +
                ", customer=" + customer +
                '}';
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }


}
