package com.eazybytes.securityDemo.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakOpacRoleConverter implements OpaqueTokenAuthenticationConverter {
    @Override
    public Authentication convert(String introspectedToken, OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
         String username = authenticatedPrincipal.getAttribute("preferred_username");
         Map<String,Object> realmAccess = authenticatedPrincipal.getAttribute("realm_access");

        Collection<GrantedAuthority> authorities = ((List<String>)realmAccess.get("roles"))
                .stream().map(s-> new SimpleGrantedAuthority("ROLE_"+s)).collect(Collectors.toUnmodifiableList());

        return UsernamePasswordAuthenticationToken.authenticated(username,null,authorities);
    }
}
