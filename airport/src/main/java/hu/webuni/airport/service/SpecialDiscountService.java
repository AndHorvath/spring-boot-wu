package hu.webuni.airport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.airport.config.AirportConfigProperties;

@Service
public class SpecialDiscountService implements DiscountService {
	
	// --- attributes ---------------------------------------------------------
	
	@Autowired
	private AirportConfigProperties configProperties;
	
	// --- getters and setters ------------------------------------------------

	public AirportConfigProperties getConfigProperties() { return configProperties; }
	
	// --- public methods -----------------------------------------------------

	@Override
	public int getDiscountPercent(int totalPrice) {
		return 
			totalPrice > configProperties.getDiscount().getSpecialConfig().getLimit()
			? configProperties.getDiscount().getSpecialConfig().getPercent()
			: configProperties.getDiscount().getDefaultConfig().getPercent();
	}
}