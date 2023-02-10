package com.artostapyshyn.automarketplace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    			.csrf()
    			.disable()
    			.authorizeHttpRequests()
    			.requestMatchers("/").permitAll() 
    			.requestMatchers("/sign-up").permitAll() 
    			.requestMatchers("/sellers/**").permitAll()
    			.requestMatchers(HttpMethod.DELETE, "/sellers/**").hasRole(Role.ROLE_ADMIN.name())
    			.anyRequest()
    			.authenticated()
    			.and()
    			.formLogin()
    			.loginPage("/sign-in").permitAll()
    			.defaultSuccessUrl("/profile")
    			.and()
    			.logout()
    			.logoutUrl("/logout")
    			.logoutSuccessUrl("/")
    			.and()
    			.httpBasic();

		return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());
    }

}
