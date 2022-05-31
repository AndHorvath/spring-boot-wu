package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.model.DataView;
import hu.webuni.hr.ah.model.TestCompany;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    // --- attributes ---------------------------------------------------------

    private Map<String, CompanyDto> companyDtos;

    // --- constructors -------------------------------------------------------

    public CompanyController() {
        companyDtos = new LinkedHashMap<>();
    }

    // --- simple company endpoints -------------------------------------------

    @GetMapping(params = "full=true")
    public List<CompanyDto> getCompanies() {
        return getCompanyList();
    }

    @GetMapping(value = "/{registrationNumber}", params = "full=true")
    public CompanyDto getCompanyByRegistrationNumber(@PathVariable String registrationNumber) {
        validateParameter(registrationNumber);
        return companyDtos.get(registrationNumber);
    }

    @GetMapping("/test")
    public List<CompanyDto> getTestData() {
        initializeTestData();
        return companyDtos.values().stream().toList();
    }

    @PostMapping
    public CompanyDto addCompany(@RequestBody CompanyDto companyDto) {
        String registrationNumber = companyDto.getRegistrationNumber();
        companyDtos.put(registrationNumber, companyDto);
        return companyDtos.get(registrationNumber);
    }

    @PutMapping("/{registrationNumber}")
    public CompanyDto updateCompany(@PathVariable String registrationNumber, @RequestBody CompanyDto companyDto) {
        validateParameter(registrationNumber);
        companyDtos.put(registrationNumber, createCompanyDto(registrationNumber, companyDto));
        return companyDtos.get(registrationNumber);
    }

    @DeleteMapping
    public void deleteCompanies() {
        companyDtos.clear();
    }

    @DeleteMapping("/{registrationNumber}")
    public void deleteCompanyByRegistrationNumber(@PathVariable String registrationNumber) {
        companyDtos.remove(registrationNumber);
    }

    // --- company view endpoints ---------------------------------------------

    @GetMapping
    @JsonView(DataView.BaseDataView.class)
    public List<CompanyDto> getCompanies(@RequestParam(required = false) Boolean full) {
        return getCompanyList();
    }

    @GetMapping("/{registrationNumber}")
    @JsonView(DataView.BaseDataView.class)
    public CompanyDto getCompanyByRegistrationNumber(
        @PathVariable String registrationNumber, @RequestParam(required = false) Boolean full) {

        validateParameter(registrationNumber);
        return companyDtos.get(registrationNumber);
    }

    // --- company employee list endpoints ------------------------------------

    @PostMapping("/{registrationNumber}/employees")
    public CompanyDto addEmployeeToCompany(
        @PathVariable String registrationNumber, @RequestBody EmployeeDto employeeDto) {

        validateParameter(registrationNumber);
        companyDtos.get(registrationNumber).addEmployee(employeeDto);
        return companyDtos.get(registrationNumber);
    }

    @PutMapping("/{registrationNumber}/employees")
    public CompanyDto updateEmployeeListInCompany(
        @PathVariable String registrationNumber, @RequestBody List<EmployeeDto> employeeDtos) {

        validateParameter(registrationNumber);
        companyDtos.get(registrationNumber).setEmployees(employeeDtos);
        return companyDtos.get(registrationNumber);
    }

    @DeleteMapping("/{registrationNumber}/employees/{employeeId}")
    public void deleteEmployeeInCompanyById(@PathVariable String registrationNumber, @PathVariable long employeeId) {
        validateParameter(registrationNumber);
        companyDtos.get(registrationNumber).removeEmployeeById(employeeId);
    }

    // --- private methods ----------------------------------------------------

    private void initializeTestData() {
        initializeCompanyDtos();
        TestCompany.initializeDtoList().forEach(this::updateCompanyDtos);
    }

    private void initializeCompanyDtos() {
        if (!companyDtos.isEmpty()) {
            companyDtos.clear();
        }
    }

    private void validateParameter(String registrationNumber) {
        if (!companyDtos.containsKey(registrationNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void updateCompanyDtos(CompanyDto companyDto) {
        companyDtos.put(companyDto.getRegistrationNumber(), companyDto);
    }

    private CompanyDto createCompanyDto(String registrationNumber, CompanyDto companyDto) {
        return new CompanyDto(
            registrationNumber,
            companyDto.getName(),
            companyDto.getAddress(),
            companyDto.getEmployees()
        );
    }

    private List<CompanyDto> getCompanyList() {
        return companyDtos.values().stream().toList();
    }
}