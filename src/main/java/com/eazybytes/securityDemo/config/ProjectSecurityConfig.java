package com.eazybytes.securityDemo.config;

import com.eazybytes.securityDemo.exceptionhandling.CustomAccessDeniedHandler;
import com.eazybytes.securityDemo.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.eazybytes.securityDemo.filters.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;
import org.springframework.security.web.RequestMatcherRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("!prod")
@Configuration
public class ProjectSecurityConfig {

   /* @Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
    private String intrspectionUri;
    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
    private String clientSecret;*/

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        OpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter = new KeycloakOpacRoleConverter();

        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // the resources dont exist, just for demonstration purposes
        //http.sessionManagement(smc ->smc.invalidSessionUrl("/InvalidSession").maximumSessions(1)
        //        .maxSessionsPreventsLogin(true)
        //        .expiredUrl("/expiredSession"));
        http.requiresChannel(rcc ->rcc.anyRequest().requiresInsecure()); //ONLY HTTP
        // note that /error is required to return error payloads!!
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/myAccount").hasRole("ADMIN")
                        .requestMatchers("/myBalance").hasRole("USER")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/error","/notices","/contact").permitAll()
                );

        // Resource server validates JWT locally. This KWT has roles...

        http.oauth2ResourceServer(rsc ->
                rsc.jwt( jwtc ->
                        jwtc.jwtAuthenticationConverter(jwtAuthenticationConverter)
                )
        );

        /* for opaque token
        http.oauth2ResourceServer(rsc ->
                rsc.opaqueToken(otc ->
                    otc.authenticationConverter(opaqueTokenAuthenticationConverter)
                        .introspectionUri(intrspectionUri)
                        .introspectionClientCredentials(clientId,clientSecret)
                )
            );

         */

        http.exceptionHandling(ehc ->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        http.csrf(csrfConfig ->csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers("/contact","/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.cors(cc->cc.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization")); // to send JWT in response
                config.setMaxAge(3600L);
                return config;
            }
        }));
        return http.build();
    }



}


