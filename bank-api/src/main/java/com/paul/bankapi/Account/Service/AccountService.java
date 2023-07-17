package com.paul.bankapi.Account.Service;

import com.paul.bankapi.Account.Model.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Optional;

@Api(tags = "Account Service")
public interface AccountService {
    @ApiOperation(value = "Add Account", notes = "Create and add a new Account for the specified customer")
    Account addAccount(Account account, Integer customerId);

    @ApiOperation(value = "Remove Account", notes = "Remove the specified Account from the customer")
    boolean removeAccount(Integer accountId, Integer customerId);

    @ApiOperation(value = "Find Account by ID", notes = "Retrieve an Account by its unique ID")
    Optional<Account> findAccountById(Integer accountId);

    @ApiOperation(value = "Update Account", notes = "Update an existing Account")
    Account updateAccount(Account account);
}
