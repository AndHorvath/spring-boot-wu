package hu.webuni.airport.web;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import hu.webuni.airport.mapper.AirportMapper;
import hu.webuni.airport.model.Airport;
import hu.webuni.airport.service.AirportService;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
	
	// --- attributes ---------------------------------------------------------
	
	@Autowired
	AirportService airportService;
	
	@Autowired
	AirportMapper airportMapper;
	
	// --- public methods -----------------------------------------------------
	
	@GetMapping
	public List<AirportDto> getAll() {
		return airportMapper.airportsToDtos(airportService.findAll());
	}
	
	@GetMapping("/{id}")
	public AirportDto getById(@PathVariable long id) {
		Airport airport = airportService.findById(id).orElseThrow(callNotFoundException());	
		return airportMapper.airportToDto(airport);
	}
	
	@PostMapping
	public AirportDto createAirport(@RequestBody @Valid AirportDto airportDto) {
		Airport airport = airportService.save(airportMapper.dtoToAirport(airportDto));
		return airportMapper.airportToDto(airport);
	}
	
	@PutMapping("/{id}")
	public AirportDto modifyAirport(@PathVariable long id, @RequestBody @Valid AirportDto airportDto) {
		airportDto.setId(id);
		try {
			return airportMapper.airportToDto(airportService.update(airportMapper.dtoToAirport(airportDto)));
		} catch (NoSuchElementException exception) {
			throw callNotFoundException().get();
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteAirport(@PathVariable long id) {
		try {
			airportService.delete(id);
		} catch (NoSuchElementException exception) {
			throw callNotFoundException().get();
		}
	}
	
	// --- private methods ----------------------------------------------------
	
	private Supplier<ResponseStatusException> callNotFoundException() {
		return () -> new ResponseStatusException(HttpStatus.NOT_FOUND); 
	}
}