package hu.webuni.airport.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.airport.model.Airport;

@Service
public class AirportService {
	
	// --- attributes ---------------------------------------------------------
	
	@PersistenceContext
	EntityManager entityManager;
	
	// --- public methods -----------------------------------------------------
	
	public List<Airport> findAll() {
		return entityManager.createQuery("SELECT a FROM Airport a", Airport.class).getResultList();
	}
	
	public Airport findById(long id) {
		return entityManager.find(Airport.class, id);
	}
	
	@Transactional
	public Airport save(Airport airport) {
		checkUniqueIata(airport);
		entityManager.persist(airport);;
		return airport;
	}
	
	@Transactional
	public Airport update(Airport airport) {
		checkUniqueIata(airport);
		return entityManager.merge(airport);
	}
	
	@Transactional
	public void delete(long id) {
		entityManager.remove(findById(id));
	}
	
	// --- private methods ----------------------------------------------------
	
	private void checkUniqueIata(Airport airport) {
		if (isNotUniqueIata(airport)) {
			throw new NonUniqueIataException(airport.getIata());
		}
	}
	
	private boolean isNotUniqueIata(Airport airport) {
		return 0 < entityManager
			.createNamedQuery("Airport.countByIata", Long.class)
			.setParameter("iata", airport.getIata())
			.setParameter("id", airport.getId())
			.getSingleResult();
	}
}