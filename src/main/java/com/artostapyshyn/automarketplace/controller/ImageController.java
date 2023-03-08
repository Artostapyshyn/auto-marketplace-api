package com.artostapyshyn.automarketplace.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.artostapyshyn.automarketplace.entity.Image;
import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.enums.Role;
import com.artostapyshyn.automarketplace.service.ImageService;
import com.artostapyshyn.automarketplace.service.SaleAdvertisementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("api/v1/sale-advertisements")
@SecurityRequirement(name = "auto-marketplace")
@AllArgsConstructor
public class ImageController {
	
	private final ImageService imageService;
	
	private final SaleAdvertisementService saleAdvertisementService;
	
	@Operation(summary = "Get all images.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }) }) 
	@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
	@GetMapping("/images")
	public List<Image> listAllImages() {
		log.info("Listing all images");
		return imageService.getAllImages().stream().toList();
	}
	
	@Operation(summary = "Get image by id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }) }) 
	@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
	@GetMapping("/images{id}")
    public ResponseEntity<byte[]> getImageById(@PathParam("id") Long id) {
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
			
			addImageToAdvertisement(file, saleAdvertisement);
			
			response.add("Image uploaded successfully " + file.getOriginalFilename());
			response.add(saleAdvertisement);
			log.info("Successful upload " + file.getOriginalFilename());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.add("You don't have accsess for this action");
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}
    }
    
	private void addImageToAdvertisement(MultipartFile file, SaleAdvertisement saleAdvertisement) throws IOException {
		Image image = toImageEntity(file);
		image.setSaleAdvertisement(saleAdvertisement);
		saleAdvertisement.getImages().add(image);
		saleAdvertisementService.save(saleAdvertisement);
	}
	
	@Operation(summary = "Delete image by id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete image by id", content = {
					@Content(mediaType = "application/json", examples = @ExampleObject(value = "[\r\n"
							+ "  \"Image has been deleted\"\r\n" + "]")) }) })
	@PreAuthorize("hasAnyRole('SELLER')")
	@DeleteMapping("images/delete{id}")
	public ResponseEntity<List<Object>> deleteImage(@PathParam("id") Long id) {
		List<Object> response = new ArrayList<>();
		
		if (checkAdminPermission()) {
			imageService.findById(id)
				.orElseThrow(() -> new RuntimeException("Couldn't find image with id - " + id.toString()));

			imageService.deleteById(id);
			response.add("Image has been deleted");
			log.info("Image - " + id + " has been deleted");
		
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		} else {
			response.add("You don't have accsess for this action");
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}
	}

	private String checkPermission() {
		Authentication loggedInSeller = SecurityContextHolder.getContext().getAuthentication();
		String email = loggedInSeller.getName();
		return email;
	}
	
	private boolean checkAdminPermission() {
		Authentication loggedInSeller = SecurityContextHolder.getContext().getAuthentication();
		Object role = loggedInSeller.getAuthorities();
		return role.equals(Role.ROLE_ADMIN.name());
	}
	
	private Image toImageEntity(MultipartFile file) throws IOException {
		Image image = new Image();
		image.setName(file.getName());
		image.setContentType(file.getContentType());
		image.setSize(file.getSize());
		image.setData(file.getBytes());
		return image;
	}
}
