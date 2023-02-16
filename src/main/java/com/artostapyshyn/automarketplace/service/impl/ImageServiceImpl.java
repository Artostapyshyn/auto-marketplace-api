package com.artostapyshyn.automarketplace.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
	public Image save(MultipartFile file) throws IOException {
		Image image = new Image();
		image.setName(StringUtils.cleanPath(file.getOriginalFilename()));
		image.setContentType(file.getContentType());
		image.setData(file.getBytes());
		image.setSize(file.getSize());

        return imageRepository.save(image);
		
	}

}
