package com.artostapyshyn.automarketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "advertisement_images")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "image_name", nullable = false)
	private String name;
	
	@Column(name = "image_content_type", nullable = false)
	private String contentType;

	@Column(name = "image_size", nullable = false)
	private Long size;

    @Lob
    @Column(name = "image_data", nullable = false)
	private byte[] data;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_advertisement_id")
    @JsonBackReference
    private SaleAdvertisement saleAdvertisement;
}
