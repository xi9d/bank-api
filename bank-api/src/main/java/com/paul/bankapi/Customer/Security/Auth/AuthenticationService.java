package com.paul.bankapi.Customer.Security.Auth;

import com.paul.bankapi.Customer.Model.Customer;
import com.paul.bankapi.Customer.Model.Role;
import com.paul.bankapi.Customer.Repository.CustomerRepository;
import com.paul.bankapi.Customer.Security.Config.TokenProvider;
import com.paul.bankapi.Customer.Security.Token.Token;
import com.paul.bankapi.Customer.Security.Token.TokenRepository;
import com.paul.bankapi.Customer.Security.Token.TokenType;
import com.paul.bankapi.Customer.Service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Api(tags = "Authentication Service")
public class AuthenticationService {
    private final TokenProvider tokenProvider;
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TokenRepository tokenRepository;
    private final CustomerRepository customerRepository;

    @ApiOperation("Register a new customer")
    public AuthenticationResponse register(RegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .pin(passwordEncoder.encode(request.getPin()))
                .role(Role.USER)
                .build();
        Customer _customer = customerService.addCustomer(customer);
        String jwtToken = tokenProvider.generateToken(_customer);
        saveUserToken(_customer, jwtToken);
        return new AuthenticationResponse(_customer, jwtToken);
    }


    private void saveUserToken(Customer customer, String jwtToken) {
        Token token = Token.builder()
                .customer(customer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    @ApiOperation("Authenticate a customer")
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPin()));
        Customer user = customerRepository.findCustomerByEmail(request.getEmail()).orElseThrow();
        String jwtToken = tokenProvider.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(user, jwtToken);
}
    public void revokeAllUserTokens(Customer user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


}
