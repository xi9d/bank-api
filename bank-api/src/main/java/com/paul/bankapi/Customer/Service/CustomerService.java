package com.paul.bankapi.Customer.Service;

import com.paul.bankapi.Customer.Model.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Optional;
@Api(tags = "Customer Service")
public interface CustomerService {
    @ApiOperation("Add a new customer")
    Customer addCustomer(Customer customer);

//    boolean removeCustomerById(Integer id);

    Optional<Customer> findCustomerById(Integer id);

    Optional<Customer> getCustomerById(Integer id);


//    Customer updateCustomer(Customer customer);
}
