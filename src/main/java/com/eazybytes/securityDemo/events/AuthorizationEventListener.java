package com.eazybytes.securityDemo.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationEventListener {

    @EventListener
    public void onFailure(AuthorizationDeniedEvent authorizationDeniedEvent) {
        log.error(" user {} was denied access du to {} ",
                authorizationDeniedEvent.getAuthentication().get().getName()
        ,authorizationDeniedEvent.getAuthorizationResult().toString());
    }
}
