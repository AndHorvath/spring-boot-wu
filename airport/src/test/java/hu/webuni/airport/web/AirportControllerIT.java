package hu.webuni.airport.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.airport.dto.AirportDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AirportControllerIT {
	
	private static final String BASE_URI = "/api/airports";
	
	@Autowired
	WebTestClient webTestClient; 
	
	@Test
	void testThatCreatedAirportIsListed() throws Exception {
		List<AirportDto> airportDtosBefore = getAllAirportDtos();
		AirportDto newAirportDto = new AirportDto(100, "TestNameAppropriate", "TestIATAUniqe");
		createAirport(newAirportDto);
		
		List<AirportDto> airportDtosAfter = getAllAirportDtos();
		List<AirportDto> airportDtosAfterStart = airportDtosAfter.subList(0, airportDtosBefore.size());
		AirportDto airportDtosAfterEnd = airportDtosAfter.get(airportDtosAfter.size() - 1);
		
		assertThat(airportDtosAfterStart)
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(airportDtosBefore);
		assertThat(airportDtosAfterEnd)
			.usingRecursiveComparison()
			.isEqualTo(newAirportDto);
	}
	
	// --- private methods ----------------------------------------------------

	private List<AirportDto> getAllAirportDtos() {
		List<AirportDto> responseList = webTestClient
			.get()
			.uri(BASE_URI)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(AirportDto.class)
			.returnResult().getResponseBody();
		responseList.sort(Comparator.comparing(AirportDto::getId));
		return responseList;
	}
	
	private void createAirport(AirportDto newAirportDto) {
		webTestClient
			.post()
			.uri(BASE_URI)
			.bodyValue(newAirportDto)
			.exchange()
			.expectStatus().isOk();
	}
}