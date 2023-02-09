package com.artostapyshyn.automarketplace.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;

public interface SaleAdvertisementService {
	Optional<SaleAdvertisement> findById(Long id);
	
	SaleAdvertisement findByCreationDate(LocalDateTime dateOfCreation);
	
	SaleAdvertisement save(SaleAdvertisement saleAdvertisement);

	List<SaleAdvertisement> findAll();
	
	void deleteById(Long id);
}
