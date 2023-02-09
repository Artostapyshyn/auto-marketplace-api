package com.artostapyshyn.automarketplace.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artostapyshyn.automarketplace.entity.Seller;
import com.artostapyshyn.automarketplace.service.SellerService;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1/sellers")
@AllArgsConstructor
public class SellerController {
	
	private final SellerService sellerService;
	
	@GetMapping
	public ResponseEntity<List<Object>> getAllSellers() {
		List<Object> response = new ArrayList<>();
		response.add(sellerService.findAll());
		
		log.info("Listing all sellers");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<List<Object>> getSellerById(@PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();
		Optional<Seller> seller = Optional.of(sellerService.findById(id)
				.orElseThrow(() -> new RuntimeException("Couldn't find seller with id - " + id)));

		response.add(seller.get());
		log.info("Getting seller by id - " + id);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{email}")
	ResponseEntity<List<Object>> getSellerByEmail(@PathParam("email") String email) {
		List<Object> response = new ArrayList<>();
		Seller seller = sellerService.findByEmail(email);
		
		if(seller == null) {
			throw new RuntimeException("Couldn't find seller with email - " + email);
		}
				 
		response.add(seller);
		log.info("Getting seller by email - " + email);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{phoneNumber}")
	ResponseEntity<List<Object>> getSellerByPhoneNumber(@PathParam("phoneNumber") String phoneNumber) {
		List<Object> response = new ArrayList<>();
		Seller seller = sellerService.findByPhoneNumber(phoneNumber);
		
		if(seller == null) {
			throw new RuntimeException("Couldn't find seller with phone number - " + phoneNumber);
		}
				 
		response.add(seller);
		log.info("Getting seller by phone number - " + phoneNumber);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<List<Object>> deleteSeller(@PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();
		sellerService.findById(id)
			.orElseThrow(() -> new RuntimeException("Couldn't find seller with id - " + id));

		sellerService.deleteById(id);
		 
		response.add("Seller with id - " + id + " has been deleted");
		log.info("Seller wth id - " + id + "has been deleted");
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
