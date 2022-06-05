package hu.webuni.airport.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.airport.dto.AirportDto;
import hu.webuni.airport.model.Airport;

@Mapper(componentModel = "spring")
public interface AirportMapper {
	
	List<AirportDto> airportsToDtos(List<Airport> airports);

	AirportDto airportToDto(Airport airport);
	
	Airport dtoToAirport(AirportDto airportDto);
}