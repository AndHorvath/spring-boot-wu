package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.model.DataView;

import java.util.*;

public class CompanyDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(DataView.BaseDataView.class)
    private final String registrationNumber;

    @JsonView(DataView.BaseDataView.class)
    private String name;

    @JsonView(DataView.BaseDataView.class)
    private String address;

    private List<EmployeeDto> employees;

    // --- constructors -------------------------------------------------------

    public CompanyDto(String registrationNumber, String name, String address, List<EmployeeDto> employees) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    // --- getters and setters ------------------------------------------------

    public String getRegistrationNumber() { return registrationNumber; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<EmployeeDto> getEmployees() { return employees; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setEmployees(List<EmployeeDto> employees) { this.employees = employees; }

    // --- public methods -----------------------------------------------------

    public void addEmployee (EmployeeDto employeeDto) {
        employees = new ArrayList<>(employees);
        employees.add(employeeDto);
    }

    public void removeEmployeeById(long id) {
        employees.removeIf(employeeDto -> employeeDto.getId() == id);
    }
}