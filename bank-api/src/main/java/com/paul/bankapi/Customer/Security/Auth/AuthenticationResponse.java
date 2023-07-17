package com.paul.bankapi.Customer.Security.Auth;

import com.paul.bankapi.Customer.Model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private Customer customer;
    private String token;
}
