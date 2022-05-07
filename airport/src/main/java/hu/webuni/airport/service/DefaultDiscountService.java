package hu.webuni.airport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.airport.config.AirportConfigProperties;

@Service
public class DefaultDiscountService implements DiscountService {
	
	// --- attributes ---------------------------------------------------------

	@Autowired
	private AirportConfigProperties configProperties;
	
	// --- getters and setters ------------------------------------------------
	
	public AirportConfigProperties getAirportConfigProperties() { return configProperties; }
	
	// --- public methods -----------------------------------------------------

	@Override
	public int getDiscountPercent(int totalPrice) {
		return configProperties.getDiscount().getDefaultConfig().getPercent();
	}
}