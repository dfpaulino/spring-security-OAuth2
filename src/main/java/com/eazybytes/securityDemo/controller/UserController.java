package com.eazybytes.securityDemo.controller;

import com.eazybytes.securityDemo.model.Customer;
import com.eazybytes.securityDemo.model.LoginRequestDTO;
import com.eazybytes.securityDemo.model.LoginResponseDTO;
import com.eazybytes.securityDemo.repository.CustomerRepository;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class UserController {

    private final CustomerRepository repository;

    public UserController(CustomerRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        Optional<Customer> optionalCustomer = repository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }


}
