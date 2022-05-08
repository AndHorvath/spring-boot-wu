package hu.webuni.airport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.airport.service.DefaultDiscountService;
import hu.webuni.airport.service.DiscountService;

@Configuration
@Profile("!prod")
public class DiscountConfiguration {
	
	@Bean
	public DiscountService discountService() {
		return new DefaultDiscountService();
	}
}