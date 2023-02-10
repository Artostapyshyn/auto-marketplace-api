package com.artostapyshyn.automarketplace.service.impl;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.artostapyshyn.automarketplace.entity.Seller;
import com.artostapyshyn.automarketplace.repository.SellerRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByEmail(email);
        	if(seller != null) {
        		return new org.springframework.security.core.userdetails.User(
        				seller.getEmail(), seller.getPassword(), Set.of(seller.getRole()));
        	} else {
        		throw new UsernameNotFoundException("User doesn't exists");
        	}
    }
}
