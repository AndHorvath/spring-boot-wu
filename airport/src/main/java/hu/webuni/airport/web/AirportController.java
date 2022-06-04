package hu.webuni.airport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.airport.dto.AirportDto;

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
	public ResponseEntity<AirportDto> getById(@PathVariable long id) {
		AirportDto airportDto = airports.get(id);
		return airportDto != null ?
			ResponseEntity.ok(airportDto) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public AirportDto createAirport(@RequestBody AirportDto airportDto) {
		airports.put(airportDto.getId(), airportDto);
		return airportDto;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AirportDto> modifyAirport(
		@PathVariable long id, @RequestBody AirportDto airportDto) {
		
		if (!airports.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		airportDto.setId(id);
		airports.put(id, airportDto);
		return ResponseEntity.ok(airportDto);
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
}