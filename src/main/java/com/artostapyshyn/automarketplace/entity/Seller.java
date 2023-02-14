package com.artostapyshyn.automarketplace.entity;

import java.util.Set;

import com.artostapyshyn.automarketplace.enums.Role;
import com.artostapyshyn.automarketplace.validation.UniqueEmail;
import com.artostapyshyn.automarketplace.validation.UniquePhoneNumber;
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
import jakarta.validation.constraints.Pattern;
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
	 
    @UniquePhoneNumber
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
    message = "Phone number is not valid. Format example: +12334495788")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @UniqueEmail
    @Column(name = "email", nullable = false)
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
