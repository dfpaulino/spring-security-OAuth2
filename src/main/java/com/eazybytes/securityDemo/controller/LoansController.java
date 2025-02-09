package com.eazybytes.securityDemo.controller;

import com.eazybytes.securityDemo.model.Loans;
import com.eazybytes.securityDemo.repository.CustomerRepository;
import com.eazybytes.securityDemo.repository.LoanRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    public LoansController(LoanRepository loanRepository, CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam String email) {long id = customerRepository.findByEmail(email)
            .orElseThrow(()->new RuntimeException("Customer not found"))
            .getId();

        return loanRepository.findByCustomerIdOrderByStartDtDesc(id);
    }


}
