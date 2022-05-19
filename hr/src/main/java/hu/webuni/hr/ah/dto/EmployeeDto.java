package hu.webuni.hr.ah.dto;

import java.time.LocalDateTime;

public class EmployeeDto {

    // --- attributes ---------------------------------------------------------

    private final long id;
    private final String name;
    private final LocalDateTime dateOfEntry;

    private String position;
    private int salary;

    // --- constructors -------------------------------------------------------

    public EmployeeDto(long id, String name, LocalDateTime dateOfEntry, String position, int salary) {
        this.id = id;
        this.name = name;
        this.dateOfEntry = dateOfEntry;
        this.position = position;
        this.salary = salary;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getDateOfEntry() {	return dateOfEntry;	}
    public String getPosition() { return position; }
    public int getSalary() { return salary;	}

    public void setPosition(String position) { this.position = position; }
    public void setSalary(int salary) { this.salary = salary; }
}