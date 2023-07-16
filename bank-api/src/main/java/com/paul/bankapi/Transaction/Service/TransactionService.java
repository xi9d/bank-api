package com.paul.bankapi.Transaction.Service;

import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Transaction.Model.Transaction;

import java.util.List;

public interface TransactionService {
    Account depositAmount(Account account, Transaction transaction);

    Account withdrawAmount(Account account, Transaction transaction);

    List<Transaction> getAllTransaction(Long accountId);
}
