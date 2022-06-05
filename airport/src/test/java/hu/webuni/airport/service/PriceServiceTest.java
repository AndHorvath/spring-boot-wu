package hu.webuni.airport.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {
	
	@InjectMocks
	PriceService priceService;
	
	@Mock
	DiscountService discountService;
	
	@Test
	void testGetFinalPrice() throws Exception {
		int finalPrice = new PriceService(price -> 5).getFinalPrice(100);
		assertThat(finalPrice).isEqualTo(95);
	}
	
	@Test
	void testGetFinalPriceWithMockito() throws Exception {
		when(discountService.getDiscountPercent(100)).thenReturn(5);
		int finalPrice = priceService.getFinalPrice(100);
		assertThat(finalPrice).isEqualTo(95);
	}
}