package com.paul.bankapi.Customer.Controller;

import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Repository.CustomerRepository;
import com.paul.bankapi.Customer.Service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/customer/{customerId}")
@RequiredArgsConstructor
@Api(tags = "Customer API")
public class CustomerController {
    private final CustomerService customerService;

    @ApiOperation(value = "Get Authenticated Customer Account", notes = "Retrieve the account details of the authenticated customer")
    @GetMapping
    public ResponseEntity<Customer> getAuthenticatedCustomerAccount(@PathVariable Integer customerId) {
        Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
        return optionalCustomer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
