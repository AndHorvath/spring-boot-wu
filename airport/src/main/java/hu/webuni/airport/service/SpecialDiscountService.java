package hu.webuni.airport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.airport.config.AirportConfigurationProperties;

@Service
public class SpecialDiscountService implements DiscountService {
	
	// --- attributes ---------------------------------------------------------
	
	@Autowired
	private AirportConfigurationProperties configProperties;
	
	// --- getters and setters ------------------------------------------------

	public AirportConfigurationProperties getConfigProperties() { return configProperties; }
	
	// --- public methods -----------------------------------------------------

	@Override
	public int getDiscountPercent(int totalPrice) {
		return 
			totalPrice > configProperties.getDiscount().getSpecialConfig().getLimit()
			? configProperties.getDiscount().getSpecialConfig().getPercent()
			: configProperties.getDiscount().getDefaultConfig().getPercent();
	}
}