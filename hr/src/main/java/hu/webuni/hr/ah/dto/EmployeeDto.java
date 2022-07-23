package hu.webuni.hr.ah.dto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class EmployeeDto {

    // --- attributes ---------------------------------------------------------

    private final Long id;

    @NotBlank
    private final String name;

    @Past
    private LocalDateTime dateOfEntry;

    @NotBlank
    private String position;

    @Positive
    private int salary;

    private CompanyDto company;

    // --- constructors -------------------------------------------------------

    public EmployeeDto(Long id, String name, LocalDateTime dateOfEntry, String position, int salary, CompanyDto company) {
        this.id = id;
        this.name = name;
        this.dateOfEntry = dateOfEntry;
        this.position = position;
        this.salary = salary;
        this.company = company;
    }

    // --- getters and setters ------------------------------------------------

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getDateOfEntry() {	return dateOfEntry;	}
    public String getPosition() { return position; }
    public int getSalary() { return salary;	}
    public CompanyDto getCompany() { return company; }

    public void setDateOfEntry(LocalDateTime dateOfEntry) { this.dateOfEntry = dateOfEntry; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(int salary) { this.salary = salary; }
    public void setCompany(CompanyDto company) { this.company = company; }
}