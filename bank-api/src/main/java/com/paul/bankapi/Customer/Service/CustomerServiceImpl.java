package com.paul.bankapi.Customer.Service;


import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public boolean removeCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            customerRepository.delete(customer);
            return true;

        }
        return false;
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            return Optional.of(customer);
        }
        return Optional.empty();
    }



//    @Override
//    public Customer updateCustomer(Customer customer) {
//        Optional<Customer> optionalCustomer =findCustomerById(customer.getId());
//        if (optionalCustomer.isPresent()){
//            Customer _customer = optionalCustomer.get();
//            _customer.setAccount(customer.getAccount());
//            _customer.setName(customer.getName());
//            _customer.setEmail(customer.getEmail());
//            customerRepository.save(_customer);
//        }
//        return null;
//    }
}
