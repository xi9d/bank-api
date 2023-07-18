package com.paul.bankapi.Customer.Repository;

import com.paul.bankapi.Customer.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByEmail(String username);
}
