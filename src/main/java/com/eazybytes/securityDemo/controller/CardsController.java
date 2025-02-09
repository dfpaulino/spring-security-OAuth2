package com.eazybytes.securityDemo.controller;

import com.eazybytes.securityDemo.model.Cards;
import com.eazybytes.securityDemo.repository.CardsRepository;
import com.eazybytes.securityDemo.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    private final CardsRepository cardsRepository;
    private final CustomerRepository customerRepository;
    public CardsController(CardsRepository cardsRepository, CustomerRepository customerRepository) {
        this.cardsRepository = cardsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myCards")
    public List<Cards> getCardsDetails (@RequestParam String email) {
        long id = customerRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Customer not found"))
                .getId();
        return cardsRepository.findByCustomerId(id);
    }

}
