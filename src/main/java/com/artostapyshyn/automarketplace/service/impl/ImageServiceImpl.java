package com.artostapyshyn.automarketplace.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artostapyshyn.automarketplace.entity.Image;
import com.artostapyshyn.automarketplace.repository.ImageRepository;
import com.artostapyshyn.automarketplace.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public Optional<Image> findById(Long id) {
		return imageRepository.findById(id);
	}

	@Override
	public List<Image> getAllImages() {
		return imageRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		imageRepository.deleteById(id);
	}

}
