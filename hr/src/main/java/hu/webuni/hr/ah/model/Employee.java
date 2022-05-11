package hu.webuni.hr.ah.model;

import java.time.LocalDateTime;

public class Employee {
	
	// --- attributes ---------------------------------------------------------
	
	private final Long id;
	private final String name;
	private final LocalDateTime dateOfEntry;
	
	private String position;
	private int salary;
	
	// --- constructors -------------------------------------------------------
	
	public Employee(Long id, String name, LocalDateTime dateOfEntry, String position, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfEntry = dateOfEntry;
		this.position = position;
		this.salary = salary;
	}
	
	// --- getters and setters ------------------------------------------------

	public Long getId() { return id; }
	public String getName() { return name; }
	public LocalDateTime getDateOfEntry() {	return dateOfEntry;	}
	public String getPosition() { return position; }
	public int getSalary() { return salary;	}

	public void setPosition(String position) { this.position = position; }
	public void setSalary(int salary) { this.salary = salary; }
	
	// --- public methods -----------------------------------------------------

	@Override
	public String toString() {
		return "Employee [name=" + name + ", dateOfEntry=" + dateOfEntry + ", salary=" + salary + "]";
	}
}