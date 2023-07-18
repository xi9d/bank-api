package com.paul.bankapi.Transaction.Controller;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Account.Repository.AccountRepository;
import com.paul.bankapi.Account.Service.AccountService;
import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Repository.CustomerRepository;
import com.paul.bankapi.Transaction.Model.Transaction;
import com.paul.bankapi.Transaction.Service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/customer/account/{accountId}")
@Api(tags = "Transaction API")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final CustomerRepository customerRepository;

    @Autowired
    public TransactionController(AccountService accountService,
                                 TransactionService transactionService,
                                 CustomerRepository customerRepository) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/deposit")
    @ApiOperation(value = "Deposit amount to an account", notes = "Deposit the specified amount to the account with the given ID")
    public ResponseEntity<Account> deposit(
            @RequestBody Transaction transaction,
            @PathVariable Integer accountId
            ) {
            Optional<Account> optionalAccount = accountService.findAccountById(accountId);
            if (optionalAccount.isPresent()) {
                System.out.println("Account was found");
                Account account = optionalAccount.get();
                Account _account = transactionService.depositAmount(account, transaction);
                System.out.println("Deposited" + _account);
                return ResponseEntity.ok(_account);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "Withdraw amount from an account", notes = "Withdraw the specified amount from the account with the given ID")
    public ResponseEntity<Account> withdraw(
            @RequestBody Transaction transaction,
            @PathVariable Integer accountId
    ) {
            Optional<Account> optionalAccount = accountService.findAccountById(accountId);
            if (optionalAccount.isPresent()) {
                System.out.println("Account Was found");
                Account account = optionalAccount.get();
                Account _account = transactionService.withdrawAmount(account, transaction);
                System.out.println("Withdrawn"+_account);
                return ResponseEntity.ok(_account);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping
    @ApiOperation(value = "Get all transactions for an account", notes = "Retrieve all transactions for the account with the given ID")
    public ResponseEntity<List<Transaction>> getAllTransactions(
            @PathVariable Integer accountId
    ) {
            Optional<Account> optionalAccount = accountService.findAccountById(accountId);
        return optionalAccount.map(account -> ResponseEntity.ok(transactionService.getAllTransaction(account.getId()))).orElse(null);
    }
}
