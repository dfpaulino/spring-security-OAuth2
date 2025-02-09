package com.eazybytes.securityDemo.controller;

import com.eazybytes.securityDemo.model.AccountTransactions;
import com.eazybytes.securityDemo.repository.AccountTransactionsRepository;
import com.eazybytes.securityDemo.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    private final AccountTransactionsRepository accountTransactionsRepository;
    private final CustomerRepository customerRepository;
    public BalanceController(AccountTransactionsRepository accountTransactionsRepository, CustomerRepository customerRepository) {
        this.accountTransactionsRepository = accountTransactionsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails (@RequestParam String email) {
        long id = customerRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Customer not found"))
                .getId();
        List<AccountTransactions> accountTransactions = accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id);
        return accountTransactions;
    }

}
