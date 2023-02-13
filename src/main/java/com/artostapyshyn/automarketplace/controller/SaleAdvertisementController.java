package com.artostapyshyn.automarketplace.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.service.SaleAdvertisementService;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1/sale-advertisement")
@AllArgsConstructor
public class SaleAdvertisementController {

	private final SaleAdvertisementService saleAdvertisementService;
	
	@GetMapping
	public ResponseEntity<List<Object>> getAllAdvertisements() {
		List<Object> response = new ArrayList<>();
		response.add(saleAdvertisementService.findAll());
		
		log.info("Listing all sale advertisements");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<List<Object>> getAdvertisementById(@PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();
		Optional<SaleAdvertisement> saleAvertisement = Optional.of(saleAdvertisementService.findById(id)
				.orElseThrow(() -> new RuntimeException("Couldn't find sale advertisement with id - " + id)));

		response.add(saleAvertisement.get());
		log.info("Getting sale advertisement by id - " + id);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{creationDate}")
	ResponseEntity<List<Object>> getAdvertisementByDate(@PathParam("creationDate") LocalDateTime creationDate) {
		List<Object> response = new ArrayList<>();
		
		SaleAdvertisement saleAvertisement = saleAdvertisementService.findByCreationDate(creationDate);
			if(saleAvertisement == null)
				throw new RuntimeException("Couldn't find sale advertisement on - " + creationDate);

		response.add(saleAvertisement);
		log.info("Getting sale advertisement by creationDate - " + creationDate);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SELLER')")
	@PostMapping("/add")
	ResponseEntity<List<Object>> addAdvertisement(@RequestBody SaleAdvertisement saleAdvertisement) {
		List<Object> response = new ArrayList<>();

		SaleAdvertisement newSaleAdvertisement = saleAdvertisementService.save(saleAdvertisement);
		 
		response.add(newSaleAdvertisement);
		log.info("New sale advertisement added");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
	@PostMapping("/edit/{id}")
	ResponseEntity<List<Object>> editAdvertisement(@RequestBody SaleAdvertisement saleAdvertisement, @PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();

		saleAdvertisement.setId(id);
		SaleAdvertisement saleAdv = saleAdvertisementService.findById(saleAdvertisement.getId()).get();

		edit(saleAdvertisement, saleAdv);

		SaleAdvertisement updatedSaleAdv = saleAdvertisementService.save(saleAdv);
		response.add(updatedSaleAdv);
		log.info("Sale advertisement updated");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
	@DeleteMapping
	public ResponseEntity<List<Object>> deleteAdvertisement(@PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();
		saleAdvertisementService.findById(id)
			.orElseThrow(() -> new RuntimeException("Couldn't find seller with id - " + id));

		saleAdvertisementService.deleteById(id);
		 
		response.add("Your advertisement has been deleted");
		log.info("Sale advertisement - " + id + " has been deleted");
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	private void edit(SaleAdvertisement saleAdvertisement, SaleAdvertisement existingSaleAdvertisement) {
		existingSaleAdvertisement.setAdditionalFeatures(saleAdvertisement.getAdditionalFeatures());
		existingSaleAdvertisement.setBodyType(saleAdvertisement.getBodyType());
		existingSaleAdvertisement.setBrand(saleAdvertisement.getBrand());
		existingSaleAdvertisement.setCity(saleAdvertisement.getCity());
		existingSaleAdvertisement.setColor(saleAdvertisement.getColor());
		existingSaleAdvertisement.setEngineCapacity(saleAdvertisement.getEngineCapacity());
		existingSaleAdvertisement.setVinCode(saleAdvertisement.getVinCode());
		existingSaleAdvertisement.setDescription(saleAdvertisement.getDescription());
		existingSaleAdvertisement.setLastTechInspection(saleAdvertisement.getLastTechInspection());
		existingSaleAdvertisement.setPrice(saleAdvertisement.getPrice());
		existingSaleAdvertisement.setProductionYear(saleAdvertisement.getProductionYear());
		existingSaleAdvertisement.setType(saleAdvertisement.getBodyType());
		existingSaleAdvertisement.setVehiclePlateNumber(saleAdvertisement.getVehiclePlateNumber());
		existingSaleAdvertisement.setModel(saleAdvertisement.getModel());
	} 
}
