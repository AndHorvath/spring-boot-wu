package hu.webuni.airport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hu.webuni.airport.model.Airport;

@Service
public class AirportService {
	
	// --- attributes ---------------------------------------------------------
	
	private Map<Long, Airport> airports;
	
	// --- constructors -------------------------------------------------------
	
	public AirportService() {
		airports = initializeAirports();
	}
	
	// --- getters and setters ------------------------------------------------
	
	public Map<Long, Airport> getAirports() { return airports; }
	
	// --- public methods -----------------------------------------------------
	
	public List<Airport> findAll() {
		return new ArrayList<>(airports.values());
	}
	
	public Airport findById(long id) {
		return airports.get(id);
	}
	
	public Airport save(Airport airport) {
		checkUniqueIata(airport);
		long id = airport.getId();
		airports.put(id, airport);
		return airports.get(id);
	}
	
	public void delete(long id) {
		airports.remove(id);
	}
	
	// --- private methods ----------------------------------------------------
	
	private Map<Long, Airport> initializeAirports() {
		return new HashMap<>(Map.of(
			1L, new Airport(1, "Ferenc Liszt Airport", "BUD"),
			2L, new Airport(2, "Flughafen Berlin-Schönefeld", "SXF")
		));
	}
	
	private void checkUniqueIata(Airport airportToCheck) {
		Optional<Airport> airportWithSameIata = airports.values().stream()
			.filter(airportDto -> airportDto.getIata().equals(airportToCheck.getIata()))
			.findAny();
		if (airportWithSameIata.isPresent() && isNotUpdate(airportWithSameIata.get(), airportToCheck)) {
			throw new NonUniqueIataException(airportToCheck.getIata());
		}
	}
	
	private boolean isNotUpdate(Airport airport, Airport airportToCheck) {
		return airport.getId() != airportToCheck.getId();
	}
}