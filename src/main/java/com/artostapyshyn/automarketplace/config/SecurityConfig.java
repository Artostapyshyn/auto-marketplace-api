package com.artostapyshyn.automarketplace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.artostapyshyn.automarketplace.enums.Role;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean	
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		.authorizeHttpRequests()
    		.requestMatchers(HttpMethod.GET, "api/v1/random-advertisements").permitAll() 
    		.requestMatchers(HttpMethod.POST, "/sign-up").permitAll()
    		.requestMatchers(HttpMethod.POST, "/sign-in").permitAll() 
    		.requestMatchers(HttpMethod.GET, "/sellers/**", "/sale-advertisements/**", "/swagger-ui/**","/v3/api-docs/**").permitAll()
    		.requestMatchers(HttpMethod.DELETE, "/sellers/**").hasRole(Role.ROLE_ADMIN.name())
    		.requestMatchers(HttpMethod.DELETE, "/sale-advertisements/**").hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_SELLER.name())
    		.requestMatchers(HttpMethod.POST, "/sale-advertisements/**", "/sellers/**").hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_SELLER.name())
    		.anyRequest()
            .permitAll()
            .and()
            .httpBasic()
            .and()
            .csrf()
            .disable();

		return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
                                 AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
}
