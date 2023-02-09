package com.artostapyshyn.automarketplace.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;

@Repository
public interface SaleAdvertisementRepository extends JpaRepository<SaleAdvertisement, Long>{
	SaleAdvertisement findByCreationDate(LocalDateTime dateOfCreation);
}
