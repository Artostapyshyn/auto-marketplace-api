package com.artostapyshyn.automarketplace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.artostapyshyn.automarketplace.entity.Image;
import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.repository.ImageRepository;
import com.artostapyshyn.automarketplace.service.impl.ImageServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

	@MockBean
	private ImageRepository imageRepository;

	@InjectMocks
	private ImageServiceImpl imageService;

	Image image = Image.builder().id(1L).contentType("/png").name("Img").size(2918L).build();

	List<Image> images = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		images.add(image);
	}

	@Test
	public void testGetAllSaleAdvertisements() throws Exception {
		when(imageService.getAllImages()).thenReturn(images);

        assertEquals(images.size(), 1);
        assertEquals(images.get(0).getSize(), 2918);
	}

	@Test
	public void testGetSaleAdvertisementById() throws Exception {
		when(imageService.findById(image.getId())).thenReturn(Optional.of(image));

        Optional<Image> result = imageService.findById(image.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getContentType()).isEqualTo("/png");
	}

	@Test
	    public void testDeleteSaleAdvertisementById() {
	        when(imageService.findById(image.getId())).thenReturn(Optional.of(image));

	        imageService.deleteById(image.getId());
	        assertThat(imageService.findById(image.getSize())).isEmpty();
	    }
}
