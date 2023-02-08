package com.artostapyshyn.automarketplace.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sale_advertisements")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleAdvertisement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	
    @Column(name = "vehicle_type", nullable = false)
    private String type;
	
    @Column(name = "vehicle_brand", nullable = false)
    private String brand;
	 
    @Column(name = "vehicle_model", nullable = false)
    private String model;

    @Column(name = "production_year", nullable = false)
    private int productionYear;
    
    @Column(name = "body_type", nullable = false)
    private String bodyType;
    
    @Column(name = "engine_capacity", nullable = false)
    private String engineCapacity;
    
    @Column(name = "color", nullable = false)
    private String color;
    
    @Column(name = "additional_features", nullable = false)
    private String additionalFeatures;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "price", nullable = false)
    private double price;
    
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "vin_code", nullable = true, unique = true)
    private String vinCode;
	
	@Column(name = "vehicle_plate_number", nullable = true, unique = true)
    private String vehiclePlateNumber;
	
	@Column(name = "last_technical_inspection", nullable = true)
    private String lastTechInspection;
	
	@Column(name = "date_of_creation", nullable = false)
	private LocalDateTime dateOfCreation;

	@ManyToOne
    @JoinColumn(name = "seller_id", nullable=false)
    private Seller seller;
	
	@PrePersist
	private void init() {
		dateOfCreation = LocalDateTime.now();
	}
}
