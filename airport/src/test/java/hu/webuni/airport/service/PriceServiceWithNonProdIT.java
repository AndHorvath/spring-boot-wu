package hu.webuni.airport.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PriceServiceWithNonProdIT {
	
	@Autowired
	PriceService priceService;
	
	@Test
	void getFinalPriceWithHighPrice() throws Exception {
		int finalPrice = priceService.getFinalPrice(11_000);
		assertThat(finalPrice).isEqualTo(9_900);
	}
}