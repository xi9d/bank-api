package com.paul.bankapi.Customer.Service;

import com.paul.bankapi.Account.Model.Account;
import com.paul.bankapi.Customer.Model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer addCustomer(Customer customer);

    boolean removeCustomerById(Long id);

    Optional<Customer> findCustomerById(Long id);



//    Customer updateCustomer(Customer customer);
}
