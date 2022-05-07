package hu.webuni.airport.service;

import org.springframework.stereotype.Service;

@Service
public class PriceService {
	
	// --- attributes ---------------------------------------------------------
	
	private DiscountService discountService;
	
	// --- constructors -------------------------------------------------------

	public PriceService(DiscountService discountService) {
		this.discountService = discountService;
	}
	
	// --- getters and setters ------------------------------------------------
	
	public DiscountService getDiscountService() {
		return discountService;
	}
	
	// --- public methods -----------------------------------------------------

	public int getFinalPrice(int price) {
		return (int) (price / 100.0 * (100 - discountService.getDiscountPercent(price)));
	}
}