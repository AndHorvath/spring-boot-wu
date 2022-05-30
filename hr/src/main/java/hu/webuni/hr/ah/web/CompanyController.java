package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.model.DataView;
import hu.webuni.hr.ah.model.TestCompany;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

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
        initializeTestData();
    }

    // --- simple company endpoints -------------------------------------------

    @GetMapping
    public MappingJacksonValue getCompanies() {
        return getCompanies(null);
    }

    @GetMapping("/{registrationNumber}")
    public ResponseEntity<CompanyDto> getCompanyByRegistrationNumber(@PathVariable String registrationNumber) {
        CompanyDto companyDto = companyDtos.get(registrationNumber);
        return companyDto != null ? ResponseEntity.ok(companyDto) : ResponseEntity.notFound().build();
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
    public ResponseEntity<CompanyDto> updateCompany(
        @PathVariable String registrationNumber, @RequestBody CompanyDto companyDto) {

        if (!companyDtos.containsKey(registrationNumber)) {
            return ResponseEntity.notFound().build();
        }
        companyDtos.put(registrationNumber, createCompanyDto(registrationNumber, companyDto));
        return ResponseEntity.ok(companyDtos.get(registrationNumber));
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

    @GetMapping(params = "fullView")
    public MappingJacksonValue getCompanies(@RequestParam(required = false) Boolean fullView) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(getCompanyList());
        if (fullView == null || !fullView) {
            mappingJacksonValue.setSerializationView(DataView.BaseDataView.class);
        }
        return mappingJacksonValue;
    }

    // --- company employee list endpoints ------------------------------------

    @PostMapping("/{registrationNumber}/employees")
    public ResponseEntity<CompanyDto> addEmployeeToCompany(
        @PathVariable String registrationNumber, @RequestBody EmployeeDto employeeDto) {

        if (!companyDtos.containsKey(registrationNumber)) {
            return ResponseEntity.notFound().build();
        }
        companyDtos.get(registrationNumber).addEmployee(employeeDto);
        return ResponseEntity.ok(companyDtos.get(registrationNumber));
    }

    @PutMapping("/{registrationNumber}/employees")
    public ResponseEntity<CompanyDto> updateEmployeeListInCompany(
        @PathVariable String registrationNumber, @RequestBody List<EmployeeDto> employeeDtos) {

        if (!companyDtos.containsKey(registrationNumber)) {
            return ResponseEntity.notFound().build();
        }
        companyDtos.get(registrationNumber).setEmployees(employeeDtos);
        return ResponseEntity.ok(companyDtos.get(registrationNumber));
    }

    @DeleteMapping("/{registrationNumber}/employees/{employeeId}")
    public void deleteEmployeeInCompanyById(@PathVariable String registrationNumber, @PathVariable long employeeId) {
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