package com.paul.bankapi.Transaction.Service;
import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Account.Service.AccountService;
import com.paul.bankapi.Exception.InsufficientFundsException;
import com.paul.bankapi.Transaction.Model.Transaction;
import com.paul.bankapi.Transaction.Model.TransactionType;
import com.paul.bankapi.Transaction.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Override
    public Account depositAmount(Account account, Transaction transaction) {
        transaction.setTransactionType(TransactionType.DEPOSIT);
        account.setBalance(account.getBalance() + transaction.getAmount());
        account.addTransaction(transaction); // Assuming addTransaction() method adds the transaction to the account's list.
        System.out.println(account.getBalance());
        Account _account = accountService.updateAccount(account);
        transactionRepository.save(transaction);
        return _account;
    }

    @Override
    public Account withdrawAmount(Account account, Transaction transaction) {
        transaction.setTransactionType(TransactionType.WITHDRAW);
        if (account.getBalance() >= transaction.getAmount()) {
            account.setBalance(account.getBalance() - transaction.getAmount());
            account.addTransaction(transaction); // Assuming addTransaction() method adds the transaction to the account's list.
            System.out.println(account.getBalance());
            Account _account = accountService.updateAccount(account);
            transactionRepository.save(transaction);
            return _account;
        } else {
            // If there are insufficient funds, you might want to return an appropriate response or throw an exception.
            try {
                throw new InsufficientFundsException("Insufficient funds for withdrawal.");
            } catch (InsufficientFundsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Transaction> getAllTransaction(Long accountId) {
        Optional<Account> optionalAccount = accountService.findAccountById(accountId);
        return optionalAccount.map(Account::getTransactions).orElse(Collections.emptyList());
    }
}
