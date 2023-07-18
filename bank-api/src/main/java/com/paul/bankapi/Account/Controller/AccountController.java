package com.paul.bankapi.Account.Controller;

import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Account.Service.AccountService;
import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Repository.CustomerRepository;
import com.paul.bankapi.Customer.Service.CustomerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer/{customerId}/account")
@Api(tags = "Account API")
@RequiredArgsConstructor
public class AccountController {

private final CustomerService customerService;
private final AccountService accountService;
    @PostMapping("/create")
    @ApiOperation(value = "Create an account", notes = "Create a new account for the specified customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully created the account", response = Account.class),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    public ResponseEntity<Account> createAccount(
            @RequestBody @ApiParam(value = "Account details", required = true) Account account,
            @PathVariable Integer customerId
    ) {
      Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            optionalCustomer.get().setAccount(account);
            Account _account = accountService.addAccount(account, optionalCustomer.get().getId());
            System.out.println("Account created: " + _account);
            return ResponseEntity.ok(_account);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/balance")
    @ApiOperation(value = "Get account balance", notes = "Get the balance of the authenticated customer's account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved the account balance", response = Integer.class),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    public ResponseEntity<Integer> getBalance(@PathVariable Integer customerId) {
        Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            int balance = customer.getAccount().getBalance();
            return ResponseEntity.ok(balance);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{accountId}")
    @ApiOperation(value = "Remove an account", notes = "Remove the specified account from the customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully removed the account", response = Map.class),
            @ApiResponse(code = 404, message = "Customer or account not found")
    })
    public ResponseEntity<Map<String, Boolean>> removeAccount(
            @PathVariable @ApiParam(value = "Account ID", example = "54321") Integer accountId,
            @PathVariable Integer customerId
    ) {
        Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            boolean deleteAccount = accountService.removeAccount(accountId, optionalCustomer.get().getId());
            Map<String, Boolean> results = new HashMap<>();
            results.put("Deleted account", deleteAccount);
            return ResponseEntity.ok(results);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
