package hu.webuni.airport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.airport.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {
	long countByIataAndIdNot(String iata, long id);
}