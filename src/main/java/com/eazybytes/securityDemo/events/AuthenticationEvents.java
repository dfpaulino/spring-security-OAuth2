package com.eazybytes.securityDemo.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void authOnSuccess(AuthenticationSuccessEvent authenticationSuccessEvent) {
        log.info("user  is authenticated"+authenticationSuccessEvent.getAuthentication().getName());

    }
    @EventListener
    public void authOnFailure(AbstractAuthenticationFailureEvent failures) {

        log.error("user {} with error {}",failures.getAuthentication().getName()
        ,failures.getException().getMessage());
    }
}
