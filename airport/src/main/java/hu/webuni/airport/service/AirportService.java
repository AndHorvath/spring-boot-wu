package hu.webuni.airport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.airport.model.Airport;
import hu.webuni.airport.model.Flight;
import hu.webuni.airport.repository.AirportRepository;
import hu.webuni.airport.repository.FlightRepository;

@Service
public class AirportService {
	
	// --- attributes ---------------------------------------------------------
	
	private AirportRepository airportRepository;
	private FlightRepository flightRepository;
	
	// --- constructors -------------------------------------------------------
	
	public AirportService(AirportRepository airportRepository, FlightRepository flightRepository) {
		this.airportRepository = airportRepository;
		this.flightRepository = flightRepository;
	}
	
	// --- public methods -----------------------------------------------------
	
	public List<Airport> findAll() {
		return airportRepository.findAll();
	}
	
	public Optional<Airport> findById(long id) {
		return airportRepository.findById(id);
	}
	
	@Transactional
	public Airport save(Airport airport) {
		checkUniqueIata(airport);
		return airportRepository.save(airport);
	}
	
	@Transactional
	public Airport update(Airport airport) {
		checkId(airport.getId());
		checkUniqueIata(airport);
		return airportRepository.save(airport);
	}
	
	@Transactional
	public void delete(long id) {
		checkId(id);
		airportRepository.deleteById(id);
	}
	
	@Transactional
	public void createFlight() {
		Flight fligth = createTestFlight();
		flightRepository.save(fligth);
	}
	
	// --- private methods ----------------------------------------------------
	
	private void checkId(long id) {
		if (!airportRepository.existsById(id)) {
			throw new NoSuchElementException();
		}
	}
	
	private void checkUniqueIata(Airport airport) {
		if (isNotUniqueIata(airport)) {
			throw new NonUniqueIataException(airport.getIata());
		}
	}
	
	private boolean isNotUniqueIata(Airport airport) {
		return 0 < airportRepository.countByIataAndIdNot(airport.getIata(), airport.getId());
	}
	
	private Flight createTestFlight() {
		List<Airport> airports = findAll();
		if (airports.size() == 0) {
			throw new IllegalStateException("No airport in database");
		}
		return new Flight(
			findById(airports.get(0).getId()).get(),
			findById(airports.get(airports.size() - 1).getId()).get(),
			"T-1",
			LocalDateTime.of(2020,  10, 10, 0, 0)
		);
	}
}