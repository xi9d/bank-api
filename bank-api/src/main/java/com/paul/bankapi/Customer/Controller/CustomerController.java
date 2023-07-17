package com.paul.bankapi.Customer.Controller;

import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Api(tags = "Customer API")
public class CustomerController {
    private final CustomerRepository customerRepository;

    @ApiOperation(value = "Get Authenticated Customer Account", notes = "Retrieve the account details of the authenticated customer")
    @GetMapping
    public ResponseEntity<Customer> getAuthenticatedCustomerAccount(Authentication authentication) {
        String email = authentication.getName();
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByEmail(email);
        return optionalCustomer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
