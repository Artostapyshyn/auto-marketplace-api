package com.artostapyshyn.automarketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.service.SaleAdvertisementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1/random-advertisements")
@SecurityRequirement(name = "auto-marketplace")
@AllArgsConstructor
public class RandomController {
	
	private final SaleAdvertisementService saleAdvertisementService;
	
	@Operation(summary = "Displaying 15 random sale advertisements.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found advertisements", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SaleAdvertisement.class)) }) })
	@GetMapping
	public ResponseEntity<List<Object>> getRandomAdvertisements() {
		List<Object> response = new ArrayList<>();
		response.add(saleAdvertisementService.generateRandomSaleAdvertisements());
		
		log.info("Generating random advertisements.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
