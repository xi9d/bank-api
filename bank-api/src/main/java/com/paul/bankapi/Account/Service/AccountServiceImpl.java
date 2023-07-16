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
    public Account addAccount(Account account, Long customerId) {
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
    public boolean removeAccount(Long accountId,Long customerId) {
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
    public Optional<Account> findAccountById(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            return Optional.of(account);
        }
        return Optional.empty();
    }

    @Override
    public Account updateAccount(Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(account.getId());
        return optionalAccount.orElse(null);
    }


}
