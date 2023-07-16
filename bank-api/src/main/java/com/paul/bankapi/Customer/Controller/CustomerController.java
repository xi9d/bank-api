package com.paul.bankapi.Customer.Controller;

import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
@Api(tags = "Customer API")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ApiOperation(value = "Add a new customer", notes = "Add a new customer to the system")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer _customer = customerService.addCustomer(customer);
        return ResponseEntity.ok(_customer);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove a customer", notes = "Remove a customer from the system by ID")
    public ResponseEntity<Map<String, Boolean>> removeCustomer(
            @PathVariable @ApiParam(value = "Customer ID", example = "134") Long id
    ) {
        boolean delete = customerService.removeCustomerById(id);
        Map<String, Boolean> results = new HashMap<>();
        results.put("Deleted", delete);
        return ResponseEntity.ok(results);
    }
}
