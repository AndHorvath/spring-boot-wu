package hu.webuni.airport.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "airport")
public class AirportConfigProperties {
	
	// --- attributes ---------------------------------------------------------
	
	private Discount discount;
	
	// --- constructors -------------------------------------------------------
	
	public AirportConfigProperties() {
		discount = new Discount();
	}
	
	// --- getters and setters ------------------------------------------------
	
	public Discount getDiscount() { return discount; }
	public void setDiscount(Discount discount) { this.discount = discount; }
	
	// --- nested classes -----------------------------------------------------
	
	public static class Discount {

		private Default defaultConfig;
		private Special specialConfig;
		
		public Discount() {
			defaultConfig = new Default();
			specialConfig = new Special();
		}
		
		public Default getDefaultConfig() {	return defaultConfig; }
		public Special getSpecialConfig() {	return specialConfig; }
		
		public void setDefaultConfig(Default defaultConfig) { this.defaultConfig = defaultConfig; }
		public void setSpecialConfig(Special specialConfig) { this.specialConfig = specialConfig; }
	}
	
	public static class Default {
		
		private int percent;

		public int getPercent() { return percent; }	
		public void setPercent(int percent) { this.percent = percent; }
		
	}
	
	public static class Special {
		
		private int limit;
		private int percent;
		
		public int getLimit() {	return limit; }
		public int getPercent() { return percent; }
		
		public void setLimit(int limit) { this.limit = limit; }
		public void setPercent(int percent) { this.percent = percent; }
	}
}