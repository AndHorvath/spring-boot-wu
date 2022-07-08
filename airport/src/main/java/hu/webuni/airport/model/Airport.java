package hu.webuni.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

@Entity
@NamedQuery(
	name = "Airport.countByIata",
	query = "SELECT COUNT(a.id) FROM Airport a WHERE a.iata = :iata AND a.id != :id"
)
public class Airport {
	
	// --- attributes ---------------------------------------------------------
	
	@Id
	@GeneratedValue
	private long id;
	
	@Size(min = 3, max = 40)
	private String name;
	
	private String iata;
	
	// --- constructors -------------------------------------------------------
	
	public Airport() { }

	public Airport(long id, String name, String iata) {
		this.id = id;
		this.name = name;
		this.iata = iata;
	}
	
	// --- getters and setters ------------------------------------------------

	public long getId() { return id; }
	public String getName() { return name; }
	public String getIata() { return iata; }

	public void setId(long id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setIata(String iata) { this.iata = iata; }
}