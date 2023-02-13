package com.artostapyshyn.automarketplace.service.impl;

import java.time.LocalDateTime;
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
		return Optional.of(saleAdvertisementRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Couldn't find sale advertisement with id - "+ id)));
	}

	@Override
	public SaleAdvertisement findByCreationDate(LocalDateTime creationDate) {
		return saleAdvertisementRepository.findByCreationDate(creationDate);
	}

	@Override
	public SaleAdvertisement save(SaleAdvertisement saleAdvertisement) {
		return saleAdvertisementRepository.save(saleAdvertisement);
	}

	@Override
	public List<SaleAdvertisement> findAll() {
		return saleAdvertisementRepository.findAll().stream()
				.toList();
	}

	@Override
	public void deleteById(Long id) {
		saleAdvertisementRepository.deleteById(id);
	}

	@Override
	public List<SaleAdvertisement> generateRandomSaleAdvertisements() {
		return saleAdvertisementRepository.generateRandomSaleAdvertisements();
	}

}
