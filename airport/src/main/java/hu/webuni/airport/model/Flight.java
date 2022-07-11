package hu.webuni.airport.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Flight {
	
	// --- attributes ---------------------------------------------------------
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private Airport takeOff;
	
	@ManyToOne
	private Airport landing;
	
	private String flightNumber;
	private LocalDateTime takeOffTime;
	
	// --- constructors -------------------------------------------------------
	
	public Flight() { }

	public Flight(Airport takeOff, Airport landing, String flightNumber, LocalDateTime takeOffTime) {
		this.takeOff = takeOff;
		this.landing = landing;
		this.flightNumber = flightNumber;
		this.takeOffTime = takeOffTime;
	}
	
	// --- getters and setters ------------------------------------------------

	public long getId() { return id; }
	public Airport getTakeOff() { return takeOff; }
	public Airport getLanding() { return landing; }
	public String getFlightNumber() { return flightNumber; }
	public LocalDateTime getTakeOffTime() { return takeOffTime; }

	public void setId(long id) { this.id = id; }
	public void setTakeOff(Airport takeOff) { this.takeOff = takeOff; }
	public void setLanding(Airport landing) { this.landing = landing; }
	public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber;}
	public void setTakeOffTime(LocalDateTime takeOffTime) { this.takeOffTime = takeOffTime; }
}