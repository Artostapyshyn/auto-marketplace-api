package com.artostapyshyn.automarketplace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.artostapyshyn.automarketplace.entity.SaleAdvertisement;
import com.artostapyshyn.automarketplace.repository.SaleAdvertisementRepository;
import com.artostapyshyn.automarketplace.service.impl.SaleAdvertisementServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SaleAdvertisementServiceTest {

	@MockBean
	private SaleAdvertisementRepository saleAdvertisementRepository;

	@InjectMocks
	private SaleAdvertisementServiceImpl saleAdvertisementService;

	SaleAdvertisement saleAdvertisement = SaleAdvertisement.builder().id(1L).type("Car").brand("Mercedes-Benz")
			.model("G63").productionYear(2021).bodyType("SUV").engineCapacity("3.0").color("Balck")
			.additionalFeatures("Abs, AirBag").description(".....").price(68000).city("Lviv")
			.vinCode("4Y1SL65848Z411439").vehiclePlateNumber("BC0001II").lastTechInspection("11/12/22")
			.creationDate(LocalDateTime.now()).build();

	SaleAdvertisement saleAdvertisement2 = SaleAdvertisement.builder().id(2L).type("Car").brand("Audi").model("Q8")
			.productionYear(2021).bodyType("SUV").engineCapacity("4.0").color("Balck").additionalFeatures("Abs, AirBag")
			.description(".....").price(58000).city("Lviv").vinCode("4Y1SL65848Z412222").vehiclePlateNumber("BC0002II")
			.lastTechInspection("11/09/22").creationDate(LocalDateTime.now()).build();

	List<SaleAdvertisement> advertisements = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		advertisements.add(saleAdvertisement);
		advertisements.add(saleAdvertisement2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllSaleAdvertisements() throws Exception {
		when(((OngoingStubbing<List<SaleAdvertisement>>) saleAdvertisementService.findAll()).thenReturn(advertisements));

        assertEquals(advertisements.size(), 2);
        assertEquals(advertisements.get(0).getPrice(), 68000);
	}
	
	@Test
	public void testGetSaleAdvertisementById() throws Exception {
		when(saleAdvertisementService.findById(saleAdvertisement.getId())).thenReturn(Optional.of(saleAdvertisement));

        Optional<SaleAdvertisement> result = saleAdvertisementService.findById(saleAdvertisement.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getBrand()).isEqualTo("Mercedes-Benz");
	}

	@Test
	public void testGetSaleAdvertisementByBrand() throws Exception {
		when(saleAdvertisementService.findByBrand(saleAdvertisement.getBrand())).thenReturn(advertisements);

        List<SaleAdvertisement> result = saleAdvertisementService.findByBrand(saleAdvertisement.getBrand());

        assertFalse(result == null);
        assertThat(result.equals(saleAdvertisement));
	}

	@Test
	public void testGetSaleAdvertisementByProductionYear() throws Exception {
		when(saleAdvertisementService.findByProductionYear(saleAdvertisement.getProductionYear())).thenReturn(advertisements);

        List<SaleAdvertisement> result = saleAdvertisementService.findByProductionYear(saleAdvertisement.getProductionYear());

        assertFalse(result == null);
        assertThat(result.equals(saleAdvertisement));
	}

	@Test
	    public void testSaveSaleAdvertisement() {
	        when(saleAdvertisementService.save(saleAdvertisement)).thenReturn(saleAdvertisement);

	        SaleAdvertisement result = saleAdvertisementService.save(saleAdvertisement);

	        assertThat(result).isNotNull();
	        assertThat(result.getId()).isNotNull();
	    }

	@Test
	    public void testDeleteSaleAdvertisementById() {
	        when(saleAdvertisementService.findById(saleAdvertisement.getId())).thenReturn(Optional.of(saleAdvertisement));

	        saleAdvertisementService.deleteById(saleAdvertisement.getId());
	        assertThat(saleAdvertisementService.findByBrand(saleAdvertisement.getBrand())).isEmpty();;
	    }
}
