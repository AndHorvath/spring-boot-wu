package hu.webuni.airport.model;

import javax.validation.constraints.Size;

public class Airport {
	
	// --- attributes ---------------------------------------------------------
	
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