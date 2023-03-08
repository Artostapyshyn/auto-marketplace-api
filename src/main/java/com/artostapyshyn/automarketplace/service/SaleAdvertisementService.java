package com.artostapyshyn.automarketplace.service;

import java.util.List;
import java.util.Optional;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;

public interface SaleAdvertisementService {
	Optional<SaleAdvertisement> findById(Long id);
	
	List<SaleAdvertisement> findByType(String type);
	
	List<SaleAdvertisement> findByBrand(String brand);
	
	List<SaleAdvertisement> findByProductionYear(int year);
	
	SaleAdvertisement save(SaleAdvertisement saleAdvertisement);

	List<SaleAdvertisement> findAll();
	
	List<SaleAdvertisement> generateRandomSaleAdvertisements();
	
	void deleteById(Long id);
}
