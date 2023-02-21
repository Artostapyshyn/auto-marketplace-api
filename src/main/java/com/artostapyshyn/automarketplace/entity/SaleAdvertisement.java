package com.artostapyshyn.automarketplace.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.Hibernate;

import com.artostapyshyn.automarketplace.validation.VinCode;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@Column(name = "sale_advertisement_id")
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
    
    @VinCode
    @Column(name = "vin_code", nullable = true, unique = true)
    private String vinCode;
	
	@Column(name = "vehicle_plate_number", nullable = true, unique = true)
    private String vehiclePlateNumber;
	
	@Column(name = "last_technical_inspection", nullable = true)
    private String lastTechInspection;
	
	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
	@JsonBackReference
    private Seller seller;
	
	@OneToMany(mappedBy = "saleAdvertisement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Image> images = new ArrayList<>();
	 
	@PrePersist
	private void init() {
		creationDate = LocalDateTime.now();
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SaleAdvertisement saleAdvertisement = (SaleAdvertisement) o;
        return id != null && Objects.equals(id, saleAdvertisement.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
