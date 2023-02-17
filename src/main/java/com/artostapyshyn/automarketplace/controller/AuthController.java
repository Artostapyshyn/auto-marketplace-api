package com.artostapyshyn.automarketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artostapyshyn.automarketplace.entity.Seller;
import com.artostapyshyn.automarketplace.enums.Role;
import com.artostapyshyn.automarketplace.exceptions.InvalidPasswordException;
import com.artostapyshyn.automarketplace.service.SellerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1")
@SecurityRequirement(name = "auto-marketplace")
public class AuthController {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Operation(summary = "Sign-in to upload sale advertisements.")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Message about successful login",
            content = {@Content(mediaType = "application/json", 
            examples = @ExampleObject(
            		value = "[\r\n"
            				+ "  \"You're signed-in successfully!\"\r\n"
            				+ "]")
             )})  })
    @PostMapping("/sign-in")
    public ResponseEntity<List<Object>> authenticateUser(@RequestBody Seller seller){
    	List<Object> response = new ArrayList<>();
    	
    	try {
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                seller.getEmail(), seller.getPassword()));
    	
        SecurityContextHolder.getContext().setAuthentication(authentication);
   		} catch (AuthenticationException e) {
   			log.error("Stack trace {}", e.getMessage());
   			SecurityContextHolder.getContext().setAuthentication(null);
   			throw new InvalidPasswordException();
   		}
    	
        log.info("Seller signed-in.");
        response.add("You're signed-in successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@Operation(summary = "Sign-up new seller account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Message about successful registration",
            content = {@Content(mediaType = "application/json", 
            examples = @ExampleObject(
            		value = "[\r\n"
            				+ "  \"You're successfully registered!\"\r\n"
            				+ "]")
             )}) })
	@PostMapping("/sign-up")
    ResponseEntity<List<Object>> signUpSeller(@Valid @RequestBody Seller seller) {
        List<Object> response = new ArrayList<>();
		List<Seller> sellers = sellerService.findAll(Sort.by(Sort.Direction.ASC, "id"));

        for (Seller s : sellers) {
            
        	if (s.equals(seller)) {
                log.warn("Credentials already exists!");
                response.add("Seller already exists!");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }
        
        log.info("Registered seller: " + seller.toString());
        
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        seller.setRole(Role.ROLE_SELLER);
        
        sellerService.save(seller);
        response.add("You're successfully registered!");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
