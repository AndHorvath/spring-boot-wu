package hu.webuni.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.airport.service.AirportService;
import hu.webuni.airport.service.PriceService;

@SpringBootApplication
public class AirportApplication implements CommandLineRunner {
	
	// --- attributes ---------------------------------------------------------
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private AirportService airportService;
	
	// --- getters and setters ------------------------------------------------
	
	public PriceService getPriceService() {	return priceService; }
	
	// --- public methods -----------------------------------------------------

	public static void main(String[] args) {
		SpringApplication.run(AirportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(priceService.getFinalPrice(200));
		System.out.println(priceService.getFinalPrice(20000));
		
		airportService.createFlight();
	}
}