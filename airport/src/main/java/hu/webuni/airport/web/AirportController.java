package hu.webuni.airport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.airport.dto.AirportDto;
import hu.webuni.airport.service.NonUniqueIataException;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
	
	// --- attributes ---------------------------------------------------------
	
	private Map<Long, AirportDto> airports;
	
	// --- constructors -------------------------------------------------------

	public AirportController() {
		airports = initializeAirports();
	}
	
	// --- getters and setters ------------------------------------------------

	public final Map<Long, AirportDto> getAirports() { return airports; }
	
	// --- public methods -----------------------------------------------------
	
	@GetMapping
	public List<AirportDto> getAll() {
		return new ArrayList<>(airports.values());
	}
	
	@GetMapping("/{id}")
	public AirportDto getById(@PathVariable long id) {
		AirportDto airportDto = airports.get(id);
		if (airportDto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return airportDto;
	}
	
	@PostMapping
	public AirportDto createAirport(@RequestBody @Valid AirportDto airportDto) {
		checkUniqueIata(airportDto);
		airports.put(airportDto.getId(), airportDto);
		return airportDto;
	}
	
	@PutMapping("/{id}")
	public AirportDto modifyAirport(@PathVariable long id, @RequestBody @Valid AirportDto airportDto) {		
		if (!airports.containsKey(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		checkUniqueIata(airportDto);
		airportDto.setId(id);
		airports.put(id, airportDto);
		return airportDto;
	}
	
	@DeleteMapping("/{id}")
	public void deleteAirport(@PathVariable long id) {
		airports.remove(id);
	}
	
	// --- private methods ----------------------------------------------------
	
	private Map<Long, AirportDto> initializeAirports() {
		return new HashMap<>(Map.of(
			1L, new AirportDto(1, "Ferenc Liszt Airport", "BUD"),
			2L, new AirportDto(2, "Flughafen Berlin-Schönefeld", "SXF")
		));
	}
	
	private void checkUniqueIata(AirportDto airportDtoToCheck) {
		Optional<AirportDto> airportWithSameIata = airports.values().stream()
			.filter(airportDto -> airportDto.getIata().equals(airportDtoToCheck.getIata()))
			.findAny();
		if (airportWithSameIata.isPresent() && airportWithSameIata.get().getId() != airportDtoToCheck.getId()) {
			throw new NonUniqueIataException(airportDtoToCheck.getIata());
		}
	}
}