package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.model.DataView;
import hu.webuni.hr.ah.model.TestCompany;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    // --- attributes ---------------------------------------------------------

    private Map<Long, CompanyDto> companyDtos;

    // --- constructors -------------------------------------------------------

    public CompanyController() {
        companyDtos = new TreeMap<>();
    }

    // --- simple company endpoints -------------------------------------------

    @GetMapping(params = "full=true")
    public List<CompanyDto> getCompanies() {
        return getCompanyList();
    }

    @GetMapping(value = "/{id}", params = "full=true")
    public CompanyDto getCompanyById(@PathVariable long id) {
        validateParameter(id);
        return companyDtos.get(id);
    }

    @GetMapping("/test")
    public List<CompanyDto> getTestData() {
        initializeTestData();
        return companyDtos.values().stream().toList();
    }

    @PostMapping
    public CompanyDto addCompany(@RequestBody CompanyDto companyDto) {
        long id = companyDto.getId();
        companyDtos.put(id, companyDto);
        return companyDtos.get(id);
    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        validateParameter(id);
        companyDtos.put(id, createCompanyDto(id, companyDto));
        return companyDtos.get(id);
    }

    @DeleteMapping
    public void deleteCompanies() {
        companyDtos.clear();
    }

    @DeleteMapping("/{id}")
    public void deleteCompanyById(@PathVariable long id) {
        companyDtos.remove(id);
    }

    // --- company view endpoints ---------------------------------------------

    @GetMapping
    @JsonView(DataView.BaseDataView.class)
    public List<CompanyDto> getCompanies(@RequestParam(required = false) Boolean full) {
        return getCompanyList();
    }

    @GetMapping("/{id}")
    @JsonView(DataView.BaseDataView.class)
    public CompanyDto getCompanyById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        validateParameter(id);
        return companyDtos.get(id);
    }

    // --- company employee list endpoints ------------------------------------

    @PostMapping("/{id}/employees")
    public CompanyDto addEmployeeToCompany(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        validateParameter(id);
        companyDtos.get(id).addEmployee(employeeDto);
        return companyDtos.get(id);
    }

    @PutMapping("/{id}/employees")
    public CompanyDto updateEmployeeListInCompany(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        validateParameter(id);
        companyDtos.get(id).setEmployees(employeeDtos);
        return companyDtos.get(id);
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public void deleteEmployeeInCompanyById(@PathVariable long companyId, @PathVariable long employeeId) {
        validateParameter(companyId);
        companyDtos.get(companyId).removeEmployeeById(employeeId);
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

    private void validateParameter(long id) {
        if (!companyDtos.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void updateCompanyDtos(CompanyDto companyDto) {
        companyDtos.put(companyDto.getId(), companyDto);
    }

    private CompanyDto createCompanyDto(long id, CompanyDto companyDto) {
        return new CompanyDto(
            id,
            companyDto.getRegistrationNumber(),
            companyDto.getName(),
            companyDto.getAddress(),
            companyDto.getEmployees()
        );
    }

    private List<CompanyDto> getCompanyList() {
        return companyDtos.values().stream().toList();
    }
}