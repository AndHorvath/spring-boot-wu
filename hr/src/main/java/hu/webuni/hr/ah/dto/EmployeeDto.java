package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.EmployeeDataView;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class EmployeeDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(EmployeeDataView.IdentifierView.class)
    private final long id;

    @NotBlank
    @JsonView(EmployeeDataView.IdentifierView.class)
    private final String name;

    @Past
    @JsonView(EmployeeDataView.BaseDataView.class)
    private LocalDateTime dateOfEntry;

    @Valid
    @JsonView(EmployeeDataView.BaseDataView.class)
    private PositionDto position;

    @Positive
    @JsonView(EmployeeDataView.BaseDataView.class)
    private int salary;

    @JsonView(EmployeeDataView.DetailedDataView.class)
    private CompanyDto company;

    // --- constructors -------------------------------------------------------

    public EmployeeDto(long id, String name, LocalDateTime dateOfEntry,
                       PositionDto position, int salary, CompanyDto company) {
        this.id = id;
        this.name = name;
        this.dateOfEntry = dateOfEntry;
        this.position = position;
        this.salary = salary;
        this.company = company;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getDateOfEntry() {	return dateOfEntry;	}
    public PositionDto getPosition() { return position; }
    public int getSalary() { return salary;	}
    public CompanyDto getCompany() { return company; }

    public void setSalary(int salary) { this.salary = salary; }
}