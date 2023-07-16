package com.paul.bankapi.Customer.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paul.bankapi.Account.Model.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PrimaryKeyJoinColumn
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore // Ignore account field during serialization to avoid circular reference
    private Account account;
}
