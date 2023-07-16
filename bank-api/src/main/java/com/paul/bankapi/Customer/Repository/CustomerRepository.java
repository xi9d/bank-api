package com.paul.bankapi.Customer.Repository;

import com.paul.bankapi.Customer.Model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
