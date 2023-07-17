package com.paul.bankapi.Customer.Controller;

import com.paul.bankapi.Customer.Security.Auth.AuthenticationRequest;
import com.paul.bankapi.Customer.Security.Auth.AuthenticationResponse;
import com.paul.bankapi.Customer.Security.Auth.AuthenticationService;
import com.paul.bankapi.Customer.Security.Auth.RegistrationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "Authentication API")
@RequiredArgsConstructor
public class CustomerAuthenticationController {
    private final AuthenticationService authenticationService;

    @ApiOperation(value = "Register a new customer", notes = "Create a new customer account and obtain an authentication token")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request) throws DataIntegrityViolationException {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @ApiOperation(value = "Authenticate a customer", notes = "Authenticate a customer using email and PIN and obtain an authentication token")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws DataIntegrityViolationException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
