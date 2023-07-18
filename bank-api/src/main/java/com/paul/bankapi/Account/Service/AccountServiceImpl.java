package com.paul.bankapi.Account.Service;

import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Account.Repository.AccountRepository;
import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    @Override
    public Account addAccount(Account account, Integer customerId) {
        Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setAccount(account);
            account.setCustomer(customer);
            Account savedAccount = accountRepository.save(account);
            return savedAccount;
        }
        return null;

    }

    @Override
    public boolean removeAccount(Integer accountId,Integer customerId) {
        Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
        if (optionalCustomer.isPresent()){
            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()){
                Account account = optionalAccount.get();
                accountRepository.delete(account);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Account> findAccountById(Integer accountId) {
        Optional<Account> optionalAccount = accountRepository.findAccountById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            return Optional.of(account);
        }
        return Optional.empty();
    }

    @Override
    public Account updateAccount(Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(account.getId());
        if (optionalAccount.isPresent()){
            Account _account = optionalAccount.get();
            _account.setBalance(account.getBalance());
            _account.setAccountName(account.getAccountName());
            _account.setCustomer(account.getCustomer());
            _account.setTransactions(account.getTransactions());
            accountRepository.save(_account);
            return _account;
        }
        return null;

    }


}
