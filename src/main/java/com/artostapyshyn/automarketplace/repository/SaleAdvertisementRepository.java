package com.artostapyshyn.automarketplace.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;

@Repository
public interface SaleAdvertisementRepository extends JpaRepository<SaleAdvertisement, Long>{
	SaleAdvertisement findByCreationDate(LocalDateTime creationDate);
	
	@Query(nativeQuery=true, value="SELECT * FROM sale_advertisements ORDER BY random() LIMIT 15")
	List<SaleAdvertisement> generateRandomSaleAdvertisements();
}
