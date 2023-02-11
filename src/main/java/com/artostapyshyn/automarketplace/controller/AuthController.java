package com.artostapyshyn.automarketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artostapyshyn.automarketplace.entity.Seller;
import com.artostapyshyn.automarketplace.enums.Role;
import com.artostapyshyn.automarketplace.service.SellerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class AuthController {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/sign-up")
    ResponseEntity<List<Object>> signUpSeller(@Valid @RequestBody Seller seller) {
        List<Object> response = new ArrayList<>();
		List<Seller> sellers = sellerService.findAll();

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
