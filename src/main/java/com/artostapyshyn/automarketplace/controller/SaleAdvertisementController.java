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

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.exceptions.AdvertisementNotFoundException;
import com.artostapyshyn.automarketplace.service.SaleAdvertisementService;
import com.artostapyshyn.automarketplace.service.SellerService;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1/sale-advertisements")
@AllArgsConstructor
public class SaleAdvertisementController {

	public final SaleAdvertisementService saleAdvertisementService;
	
	public final SellerService sellerService;

	@GetMapping
	public ResponseEntity<List<Object>> getAllAdvertisements(@PathParam("id") Long id, @PathParam("brand") String brand, 
										@PathParam("type") String type, @PathParam("year") Integer year ) {
		List<Object> response = new ArrayList<>();
		
		if (id != null) {
            return getAdvertisementById(id);
		} else if(type != null) {
			return getAdvertisementByType(type);
		} else if(brand != null) {
			return getAdvertisementByBrand(brand);
		} else if (year != null) {
			return getAdvertisementByProductionYear(year);
		}
		
		response.add(saleAdvertisementService.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		log.info("Listing all sale advertisements");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	ResponseEntity<List<Object>> getAdvertisementById(Long id) {
		List<Object> response = new ArrayList<>();
		Optional<SaleAdvertisement> saleAdvertisementById = saleAdvertisementService.findById(id);
		
		if (saleAdvertisementById.isEmpty()) {
			throw new AdvertisementNotFoundException(id.toString());
		} else {
			response.add(saleAdvertisementById.get());
			log.info("Getting sale advertisement by id - " + id);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@GetMapping("{brand}")
	ResponseEntity<List<Object>> getAdvertisementByBrand(String brand) {
		List<Object> response = new ArrayList<>();
		List<SaleAdvertisement> saleAdvertisementByBrand = saleAdvertisementService.findByBrand(brand);
			
		if (saleAdvertisementByBrand.isEmpty()) {
			throw new AdvertisementNotFoundException(brand);
		} else {
			response.add(saleAdvertisementByBrand);
			log.info("Listing sale advertisements with vehicle brand - " + brand);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@GetMapping("{type}")
	ResponseEntity<List<Object>> getAdvertisementByType(String type) {
		List<Object> response = new ArrayList<>();
		List<SaleAdvertisement> saleAdvertisementByType = saleAdvertisementService.findByType(type);
			
		if (saleAdvertisementByType.isEmpty()) {
			throw new AdvertisementNotFoundException(type);
		} else {
			response.add(saleAdvertisementByType);
			log.info("Getting all sale advertisements with type - " + type);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	 
	@GetMapping("{year}")
	ResponseEntity<List<Object>> getAdvertisementByProductionYear(int year) {
		List<Object> response = new ArrayList<>();
		List<SaleAdvertisement> saleAdvertisementByYear = saleAdvertisementService.findByProductionYear(year);
			
		if (saleAdvertisementByYear.isEmpty()) {
			throw new AdvertisementNotFoundException("Couldn't find any sale advertisements with year" + year);
		} else {
			response.add(saleAdvertisementByYear);
			log.info("Listing sale advertisements by vehicle production year - " + year);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	} 
	
	@PreAuthorize("hasRole('SELLER')")
	@PostMapping("/add")
	ResponseEntity<List<Object>> addAdvertisement(@RequestBody SaleAdvertisement saleAdvertisement) {
		List<Object> response = new ArrayList<>();

		String currentSellerEmail = checkPermission();
		
		saleAdvertisement.setSeller(sellerService.findByEmail(currentSellerEmail));
		SaleAdvertisement newSaleAdvertisement = saleAdvertisementService.save(saleAdvertisement);
		 
		response.add(newSaleAdvertisement);
		log.info("New sale advertisement added");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private String checkPermission() {
		Authentication loggedInSeller = SecurityContextHolder.getContext().getAuthentication();
		String email = loggedInSeller.getName();
		return email;
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
			.orElseThrow(() -> new AdvertisementNotFoundException(id.toString()));

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
		existingSaleAdvertisement.setDescription(saleAdvertisement.getDescription());
		existingSaleAdvertisement.setPrice(saleAdvertisement.getPrice());
		existingSaleAdvertisement.setProductionYear(saleAdvertisement.getProductionYear());
		existingSaleAdvertisement.setType(saleAdvertisement.getBodyType());
		existingSaleAdvertisement.setModel(saleAdvertisement.getModel());
	} 
}
