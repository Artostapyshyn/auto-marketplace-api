package com.artostapyshyn.automarketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artostapyshyn.automarketplace.service.SaleAdvertisementService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class MainController {
	
	private final SaleAdvertisementService saleAdvertisementService;
	
	@GetMapping
	public ResponseEntity<List<Object>> getRandomAdvertisements() {
		List<Object> response = new ArrayList<>();
		response.add(saleAdvertisementService.generateRandomSaleAdvertisements());
		
		log.info("Generating random advertisements.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
