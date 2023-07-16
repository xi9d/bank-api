package com.paul.bankapi.Account.Controller;

import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Account.Service.AccountService;
import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Repository.CustomerRepository;
import com.paul.bankapi.Customer.Service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/accounts")
@Api(tags = "Account API")
public class AccountController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @Autowired
    public AccountController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @PostMapping
    @ApiOperation(value = "Create an account", notes = "Create a new account for the specified customer")
    public ResponseEntity<Account> createAccount(
            @RequestBody Account account,
            @PathVariable @ApiParam(value = "Customer ID", example = "12345") Long customerId
    ) {
        Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            optionalCustomer.get().setAccount(account);
            Account _account = accountService.addAccount(account, customerId);
            System.out.println("Account created: " + _account);
            return ResponseEntity.ok(_account);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{accountId}")
    @ApiOperation(value = "Remove an account", notes = "Remove the specified account from the customer")
    public ResponseEntity<Map<String, Boolean>> removeAccount(
            @PathVariable @ApiParam(value = "Account ID", example = "54321") Long accountId,
            @PathVariable @ApiParam(value = "Customer ID", example = "12345") Long customerId
    ) {
        boolean deleteAccount = accountService.removeAccount(accountId, customerId);
        Map<String, Boolean> results = new HashMap<>();
        results.put("Deleted account", deleteAccount);
        return ResponseEntity.ok(results);
    }
}
