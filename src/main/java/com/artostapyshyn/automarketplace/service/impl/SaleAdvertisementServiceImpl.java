package com.artostapyshyn.automarketplace.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.repository.SaleAdvertisementRepository;
import com.artostapyshyn.automarketplace.service.SaleAdvertisementService;

@Service
public class SaleAdvertisementServiceImpl implements SaleAdvertisementService {

	@Autowired
	private SaleAdvertisementRepository saleAdvertisementRepository;
	
	@Override
	public Optional<SaleAdvertisement> findById(Long id) {
		return saleAdvertisementRepository.findById(id); 
	}

	@Override
	public SaleAdvertisement save(SaleAdvertisement saleAdvertisement) {
		return saleAdvertisementRepository.save(saleAdvertisement);
	}

	@Override
	public List<SaleAdvertisement> findAll() {
		return saleAdvertisementRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		saleAdvertisementRepository.deleteById(id);
	}

	@Override
	public List<SaleAdvertisement> generateRandomSaleAdvertisements() {
		return saleAdvertisementRepository.generateRandomSaleAdvertisements();
	}

	@Override
	public List<SaleAdvertisement> findByType(String type) {
		return  saleAdvertisementRepository.findByType(type);
	}

	@Override
	public List<SaleAdvertisement> findByBrand(String brand) {
		return saleAdvertisementRepository.findByBrand(brand);
	}

	@Override
	public List<SaleAdvertisement> findByProductionYear(int year) {
		return saleAdvertisementRepository.findByProductionYear(year);
	}

}
