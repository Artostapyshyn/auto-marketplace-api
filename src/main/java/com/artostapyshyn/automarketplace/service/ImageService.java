package com.artostapyshyn.automarketplace.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.artostapyshyn.automarketplace.entity.Image;

public interface ImageService {
	 Optional<Image> findById(Long id);
	 
	 List<Image> getAllImages();
	 
	 Image save(MultipartFile file) throws IOException;
}
