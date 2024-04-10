package com.example.primesservice.controller;

import com.example.primesservice.model.Customer;
import com.example.primesservice.service.IAuthenticationService;
import com.example.primesservice.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin({"http://127.0.0.1:5500"})
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private final IAuthenticationService authenticationService;
    private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    IAuthenticationService authenticationService,
                                    TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer){
        try{
            return authenticationService.register(customer);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Customer customer){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                customer.getUsername(), customer.getPassword()));
        return tokenService.generateToken(authentication);
    }
}