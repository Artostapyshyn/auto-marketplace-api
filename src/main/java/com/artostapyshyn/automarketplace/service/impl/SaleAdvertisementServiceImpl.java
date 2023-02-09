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
				.orElseThrow(() -> new RuntimeException("Could'nt find sale advertisement with id - "+ id)));
	}

	@Override
	public SaleAdvertisement findByCreationDate(LocalDateTime dateOfCreation) {
		return saleAdvertisementRepository.findByCreationDate(dateOfCreation);
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

}
