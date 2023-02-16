package com.artostapyshyn.automarketplace.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.artostapyshyn.automarketplace.entity.Image;
import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.exceptions.AdvertisementNotFoundException;
import com.artostapyshyn.automarketplace.service.ImageService;
import com.artostapyshyn.automarketplace.service.SaleAdvertisementService;
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
@RequestMapping("api/v1/sale-advertisements")
@SecurityRequirement(name = "auto-marketplace")
@AllArgsConstructor
public class SaleAdvertisementController {

	private final SaleAdvertisementService saleAdvertisementService;
	
	private final SellerService sellerService;
	
	private final ImageService imageService;
	
	@Operation(summary = "Get all sale advertisements")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found sale advertisements", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SaleAdvertisement.class)) }) })
	@GetMapping
	public ResponseEntity<List<Object>> getAllAdvertisements() {
		List<Object> response = new ArrayList<>();
		response.add(saleAdvertisementService.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		log.info("Listing all sale advertisements");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Get sale advertisements by it's id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found advertisement", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SaleAdvertisement.class)) }) })
	@GetMapping("/filter-by-id{id}")
	ResponseEntity<List<Object>> getAdvertisementById(@PathParam("id") Long id) {
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
	
	@Operation(summary = "Get sale advertisements by brand.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found advertisements", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SaleAdvertisement.class)) }) })
	@GetMapping("/filter-by-brand{brand}")
	ResponseEntity<List<Object>> getAdvertisementByBrand(@PathParam("brand") String brand) {
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
	
	@Operation(summary = "Get sale advertisements by type.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found advertisements", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SaleAdvertisement.class)) }) })
	@GetMapping("/filter-by-type{type}")
	ResponseEntity<List<Object>> getAdvertisementByType(@PathParam("type") String type) {
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
	
	@Operation(summary = "Get sale advertisements by production year.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found advertisements", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SaleAdvertisement.class)) }) })
	@GetMapping("/filter-by-year{year}")
	ResponseEntity<List<Object>> getAdvertisementByProductionYear(@PathParam("year") Integer year) {
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
	
	@Operation(summary = "Add sale advertisement.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Add sale advertisement", content = {
			@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\r\n"
					+ "  \"id\": 0,\r\n"
					+ "  \"type\": \"string\",\r\n"
					+ "  \"brand\": \"string\",\r\n"
					+ "  \"model\": \"string\",\r\n"
					+ "  \"productionYear\": 0,\r\n"
					+ "  \"bodyType\": \"string\",\r\n"
					+ "  \"engineCapacity\": \"string\",\r\n"
					+ "  \"color\": \"string\",\r\n"
					+ "  \"additionalFeatures\": \"string\",\r\n"
					+ "  \"description\": \"string\",\r\n"
					+ "  \"price\": 0,\r\n"
					+ "  \"city\": \"string\",\r\n"
					+ "  \"vinCode\": \"\\b4PXVWN)VD|3(((GR)\\b\",\r\n"
					+ "  \"vehiclePlateNumber\": \"string\",\r\n"
					+ "  \"lastTechInspection\": \"string\",\r\n"
					+ "  \"creationDate\": \"2023-02-15T16:04:36.780Z\" \r\n"
					+ "}")) }) })
	@PreAuthorize("hasRole('SELLER')")
	@PostMapping("/add")
	ResponseEntity<List<Object>> addAdvertisement(@Valid @RequestBody SaleAdvertisement saleAdvertisement) {
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
	@GetMapping("/images")
	public List<Image> listAllImages(@PathParam("id") Long id) {
		if (id != null)
			getFileById(id);

		log.info("Listing all images");
		return imageService.getAllImages().stream().toList();
	}
	 
	@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
	@GetMapping("/images{id}")
    public ResponseEntity<byte[]> getFileById(Long id) {
		Optional<Image> imageEntityOptional = imageService.findById(id);

		if (!imageEntityOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Image image = imageEntityOptional.get();
		return ResponseEntity.ok().body(image.getData());
    }
    
    @Operation(summary = "Add image for sale advertisement.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Upload successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SaleAdvertisement.class)) }) })
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("images/add{id}")
    public ResponseEntity<List<Object>> uploadImage(@RequestParam("image") MultipartFile file, @PathParam("id") Long id) throws IOException {
		List<Object> response = new ArrayList<>();

		String currentSellerEmail = checkPermission();

		SaleAdvertisement saleAdvertisement = saleAdvertisementService.findById(id).get();
		if (saleAdvertisement.getSeller().getEmail().equals(currentSellerEmail)) {
			saleAdvertisement.addImageToAdvertisement(imageService.save(file));

			response.add("Image uploaded successfully " + file.getOriginalFilename());
			response.add(saleAdvertisement);
			log.info("Successful upload " + file.getOriginalFilename());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.add("You don't have accsess for this action");
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}
		
    }
	
	 
	@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
	@PostMapping("/edit/{id}")
	ResponseEntity<List<Object>> editAdvertisement(@Valid @RequestBody SaleAdvertisement saleAdvertisement, @PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();

		saleAdvertisement.setId(id);
		SaleAdvertisement saleAdv = saleAdvertisementService.findById(saleAdvertisement.getId()).get();

		edit(saleAdvertisement, saleAdv);

		SaleAdvertisement updatedSaleAdv = saleAdvertisementService.save(saleAdv);
		response.add(updatedSaleAdv);
		log.info("Sale advertisement updated");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Delete sale advertisement by id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete sale advertisement by id", content = {
					@Content(mediaType = "application/json", examples = @ExampleObject(value = "[\r\n"
							+ "  \"Your advertisement has been deleted\"\r\n" + "]")) }) })
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
