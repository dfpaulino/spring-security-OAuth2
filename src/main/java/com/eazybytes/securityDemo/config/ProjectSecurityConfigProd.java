package com.eazybytes.securityDemo.config;

import com.eazybytes.securityDemo.exceptionhandling.CustomAccessDeniedHandler;
import com.eazybytes.securityDemo.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

//@Profile("prod")
//@Configuration
public class ProjectSecurityConfigProd {

    //@Autowired
    //private CustomerRepository customerRepository;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.requiresChannel(rcc ->rcc.anyRequest().requiresSecure());
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/myAccount","/myBalance","/myLoans","/myCards","/user").authenticated()
                        .requestMatchers("/error","/notices","/contact","/register").permitAll()
        );
        http.formLogin(flc ->flc.disable() );
        http.httpBasic(bcf ->bcf.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc ->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        http.csrf(cc->cc.disable());
        return http.build();
    }

    //@Bean
    //public UserDetailsService userDetailsService (CustomerRepository repository) {
    //    return new EazyBankUserDetailsService(repository);
    //}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * checks id password is compromised by verifying a page maintained by Spring.
     * It will fail if password is compromised!!  401
     * @return
     */
    //@Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}


