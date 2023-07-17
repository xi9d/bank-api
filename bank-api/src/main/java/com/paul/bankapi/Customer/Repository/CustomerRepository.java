package com.paul.bankapi.Customer.Repository;

import com.paul.bankapi.Customer.Model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Optional<Customer> findCustomerByEmail(String username);
}
