package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.CompanyDataView;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;

public class CompanyDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(CompanyDataView.IdentifierView.class)
    private final long id;

    @Size(min = 5, max = 5, message = "{companyDto.registrationNumber.size}")
    @JsonView(CompanyDataView.BaseDataView.class)
    private final String registrationNumber;

    @JsonView(CompanyDataView.IdentifierView.class)
    private String name;

    @JsonView(CompanyDataView.BaseDataView.class)
    private String address;

    @JsonView(CompanyDataView.BaseDataView.class)
    private CompanyTypeDto companyType;

    @Valid
    @JsonView(CompanyDataView.DetailedDataView.class)
    private List<EmployeeDto> employees;

    // --- constructors -------------------------------------------------------

    public CompanyDto(long id, String registrationNumber, String name, String address,
                      CompanyTypeDto companyType, List<EmployeeDto> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.companyType = companyType;
        this.employees = employees;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public CompanyTypeDto getCompanyType() { return companyType; }
    public List<EmployeeDto> getEmployees() { return employees; }
}