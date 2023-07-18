package com.paul.bankapi.Customer.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Customer.Security.Token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ApiModel(description = "The customer model")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PrimaryKeyJoinColumn
    @ApiModelProperty(notes = "The unique ID of the customer")
    private Integer id;

    @ApiModelProperty(notes = "The email address of the customer")
    private String email;

    @ApiModelProperty(notes = "The first name of the customer")
    private String firstName;

    @ApiModelProperty(notes = "The last name of the customer")
    private String lastName;

    @ApiModelProperty(notes = "The PIN (password) of the customer")
    private String pin;



    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "The role of the customer")
    private Role role = Role.USER;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore
    @ApiModelProperty(notes = "The account associated with the customer")
    private Account account;

    @OneToMany(mappedBy = "customer")
    @ApiModelProperty(notes = "The list of tokens associated with the customer")
    private List<Token> token;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pin='" + pin + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return pin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
