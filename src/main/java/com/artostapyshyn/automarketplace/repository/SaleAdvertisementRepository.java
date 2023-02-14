package com.artostapyshyn.automarketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;

@Repository
public interface SaleAdvertisementRepository extends JpaRepository<SaleAdvertisement, Long>{
	List<SaleAdvertisement> findByType(String type);
	
	List<SaleAdvertisement> findByBrand(String brand);
	
	List<SaleAdvertisement> findByProductionYear(int year);
	
	@Query(nativeQuery=true, value="SELECT * FROM sale_advertisements ORDER BY random() LIMIT 15")
	List<SaleAdvertisement> generateRandomSaleAdvertisements();
}
