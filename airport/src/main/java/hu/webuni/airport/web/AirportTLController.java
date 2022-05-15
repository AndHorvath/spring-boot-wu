package hu.webuni.airport.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.airport.dto.AirportDto;

@Controller
public class AirportTLController {
	
	// --- attributes ---------------------------------------------------------
	
	private List<AirportDto> allAirports;
	
	// --- constructors -------------------------------------------------------
	
	public AirportTLController() {
		allAirports = initializeAirports();
	}
	
	// --- getters and setters ------------------------------------------------
	
	public List<AirportDto> getAllAirports() { return allAirports; }
	
	// --- public methods -----------------------------------------------------
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/airports")
	public String listAirports(Map<String, Object> model) {
		model.put("airports", allAirports);
		model.put("newAirport", new AirportDto());
		return "airports";
	}
	
	@PostMapping("/airports")
	public String addAirport(AirportDto airportDto) {
		allAirports.add(airportDto);
		return "redirect:airports";
	}
	
	// --- private methods ----------------------------------------------------
	
	private List<AirportDto> initializeAirports() {
		return new ArrayList<>(List.of(
			new AirportDto(1, "Ferenc Liszt Airport", "BUD"),
			new AirportDto(2, "Flughafen Berlin-Schönefeld", "SXF")
		));
	}
}