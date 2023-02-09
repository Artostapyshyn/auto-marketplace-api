package com.artostapyshyn.automarketplace.service;

import java.util.List;
import java.util.Optional;

import com.artostapyshyn.automarketplace.entity.Seller;

public interface SellerService {
	Optional<Seller> findById(Long id);
	
	Seller findByEmail(String email);
	
	Seller findByPhoneNumber(String phoneNumber);
	
	Seller save(Seller seller);

	List<Seller> findAll();
	
	void deleteById(Long id);
}
