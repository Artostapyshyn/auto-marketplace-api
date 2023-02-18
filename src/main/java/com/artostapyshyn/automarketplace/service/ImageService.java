package com.artostapyshyn.automarketplace.service;

import java.util.List;
import java.util.Optional;

import com.artostapyshyn.automarketplace.entity.Image;

public interface ImageService {
	 Optional<Image> findById(Long id);
	 
	 List<Image> getAllImages();
	 
	 void deleteById(Long id);
}
