package com.eazybytes.securityDemo.controller;

import com.eazybytes.securityDemo.model.Accounts;
import com.eazybytes.securityDemo.repository.AccountsRepository;
import com.eazybytes.securityDemo.repository.CustomerRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    public AccountController(AccountsRepository accountsRepository, CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myAccount")
    public  Accounts getAccountDetails (@RequestParam String email) {
        JwtAuthenticationToken auth = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        //get user from the Authentication Object. Principal is in the Jwt token as claim
        //this is only for authorization grant code PKCE...where user needs to authenticate him self
        String user =(String)((Jwt)auth.getPrincipal()).getClaims().get("preferred_username");
        long id = customerRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Customer not found"))
                .getId();
        Accounts accounts = accountsRepository.findByCustomerId(id);
        if (accounts != null){
            return  accounts;
        } else {
            return null;
        }
    }

}
