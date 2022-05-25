package hu.webuni.hr.ah.dto;

import java.util.List;

public class CompanyDto {

    // --- attributes ---------------------------------------------------------

    private final String registrationNumber;

    private String name;
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
}