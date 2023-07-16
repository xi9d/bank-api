package com.paul.bankapi.Account.Service;

import com.paul.bankapi.Account.Model.Account;

import java.util.Optional;

public interface AccountService {
    Account addAccount(Account account,Long customerId);

    boolean removeAccount(Long accountId,Long customerId);

    Optional<Account> findAccountById(Long accountId);

    Account updateAccount(Account account);


}
