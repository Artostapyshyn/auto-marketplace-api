package com.artostapyshyn.automarketplace.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artostapyshyn.automarketplace.entity.Seller;
import com.artostapyshyn.automarketplace.enums.Role;
import com.artostapyshyn.automarketplace.exceptions.SellerNotFoundException;
import com.artostapyshyn.automarketplace.service.SellerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1/sellers")
@SecurityRequirement(name = "auto-marketplace")
@AllArgsConstructor
public class SellerController {
	
	private final SellerService sellerService;
	
	@Operation(summary = "Get all sellers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found sellers", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) }) })
	@GetMapping
	public ResponseEntity<List<Object>> getAllSellers() {
		List<Object> response = new ArrayList<>();
		response.add(sellerService.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		log.info("Listing all sellers");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Get a seller by it's id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the seller", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) })})
	@GetMapping("/find-by-id{id}")
	ResponseEntity<List<Object>> getSellerById(@PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();
		Optional<Seller> seller = sellerService.findById(id);
		if (seller.isEmpty()) {
			throw new SellerNotFoundException(id.toString());
		} else {
			response.add(seller.get());
			log.info("Getting seller by id - " + id);
		
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@Operation(summary = "Get a seller by it's email")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the seller", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) })})
	@GetMapping("/find-by-email{email}")
	ResponseEntity<List<Object>> getSellerByEmail(@PathParam("email") String email) {
		List<Object> response = new ArrayList<>();
		Seller seller = sellerService.findByEmail(email);
		
		if(seller == null) {
			throw new SellerNotFoundException(email);
		}
				 
		response.add(seller);
		log.info("Getting seller by email - " + email);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Get a seller by it's phone number")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the seller", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) }) })
	@GetMapping("/find-by-phoneNumber{phoneNumber}")
	ResponseEntity<List<Object>> getSellerByPhoneNumber(@PathParam("phoneNumber") String phoneNumber) {
		List<Object> response = new ArrayList<>();
		Seller seller = sellerService.findByPhoneNumber(phoneNumber);
		
		if(seller == null) {
			throw new SellerNotFoundException(phoneNumber);
		}
				 
		response.add(seller);
		log.info("Getting seller by phone number - " + phoneNumber);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Edit seller information.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Edited successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) }) }) 
	@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
	@PostMapping("/edit{id}")
	ResponseEntity<List<Object>> editSellerInfo(@Valid @RequestBody Seller seller, @PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();

		Long currentSellerId = checkPermission();
		
		if(currentSellerId == id || checkAdminPermission()) {
		seller.setId(currentSellerId);
		Seller existingSeller = sellerService.findById(seller.getId()).get();

		editPersonalInfo(seller, existingSeller);

		Seller updatedSeller = sellerService.save(existingSeller);
		response.add(updatedSeller);
		log.info("Seller information updated.");
		} else {
			throw new RuntimeException("You could edit only own information!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public Long checkPermission() {
		Authentication loggedInSeller = SecurityContextHolder.getContext().getAuthentication();
		String email = loggedInSeller.getName();

		Seller s  = sellerService.findByEmail(email);
		Long currentSellerId = s.getId();
		return currentSellerId;
	}
	
	private boolean checkAdminPermission() {
		Authentication loggedInSeller = SecurityContextHolder.getContext().getAuthentication();
		Object role = loggedInSeller.getAuthorities();
		return role.equals(Role.ROLE_ADMIN.name());
	}
	
	private void editPersonalInfo(Seller seller, Seller existingSeller) {
		existingSeller.setFirstName(seller.getFirstName());
		existingSeller.setLastName(seller.getLastName());
		
		if(sellerService.findByEmail(seller.getEmail()) == null) {
			existingSeller.setEmail(seller.getEmail());
		} else {
			throw new RuntimeException("Email already exists, try another one.");
		}
		existingSeller.setPhoneNumber(seller.getPhoneNumber());
	}

	@Operation(summary = "Delete seller by id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete seller by id", content = {
					@Content(mediaType = "application/json", examples = @ExampleObject(value = "[\r\n"
							+ "  \"Seller with id - id has been deleted\"\r\n" + "]")) }) })
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<List<Object>> deleteSeller(@PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();
		sellerService.findById(id)
			.orElseThrow(() -> new SellerNotFoundException(id.toString()));

		sellerService.deleteById(id);
		 
		response.add("Seller with id - " + id + " has been deleted");
		log.info("Seller wth id - " + id + " has been deleted");
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
