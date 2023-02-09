package com.artostapyshyn.automarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artostapyshyn.automarketplace.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	Seller findByPhoneNumber(String phoneNumber);
	
	Seller findByEmail(String email);
}
