package com.artostapyshyn.automarketplace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.artostapyshyn.automarketplace.entity.Seller;
import com.artostapyshyn.automarketplace.enums.Role;
import com.artostapyshyn.automarketplace.repository.SellerRepository;
import com.artostapyshyn.automarketplace.service.impl.SellerServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {

	@MockBean
	private SellerRepository sellerRepository;

	@InjectMocks
	private SellerServiceImpl sellerService;

	Seller seller = Seller.builder().id(1L).firstName("Tom").lastName("Black").phoneNumber("+380976541312")
			.email("Seller@gmail.com").password("password").role(Role.ROLE_SELLER).build();

	@Test
	public void testGetSellerById() throws Exception {
		when(sellerService.findById(seller.getId())).thenReturn(Optional.of(seller));

        Optional<Seller> result = sellerService.findById(seller.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getEmail()).isEqualTo("Seller@gmail.com");
	}

	@Test
	public void testGetSellerByEmail() throws Exception {
		when(sellerService.findByEmail(seller.getEmail())).thenReturn(seller);

        Seller result = sellerService.findByEmail(seller.getEmail());

        assertFalse(result == null);
        assertThat(result.getEmail()).isEqualTo("Seller@gmail.com");
        assertThat(result.getRole()).isEqualTo(Role.ROLE_SELLER);
	}

	@Test
	    public void testSaveSeller() {
	        when(sellerService.save(seller)).thenReturn(seller);

	        Seller result = sellerService.save(seller);

	        assertThat(result).isNotNull();
	        assertThat(result.getId()).isNotNull();
	        assertThat(result.getEmail()).isEqualTo("Seller@gmail.com");
	    }

	@Test
	    public void testDeleteSellerById() {
	        when(sellerService.findById(seller.getId())).thenReturn(Optional.of(seller));

	        sellerService.deleteById(seller.getId());
	        assertNull(sellerService.findByEmail(seller.getEmail()));
	    }
}
