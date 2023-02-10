package com.artostapyshyn.automarketplace.entity;

import java.util.Set;

import com.artostapyshyn.automarketplace.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sellers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	
    @Column(name = "first_name", nullable = false)
    private String firstName;
	
    @Column(name = "last_name", nullable = false)
    private String lastName;
	 
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    private Set<SaleAdvertisement> advertisements;
	 
}
