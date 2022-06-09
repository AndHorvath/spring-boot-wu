package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.mapper.CompanyMapper;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.model.DataView;
import hu.webuni.hr.ah.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    // --- simple company endpoints -------------------------------------------

    @GetMapping(params = "full=true")
    public List<CompanyDto> getCompanies() {
        return companyMapper.companiesToDtos(companyService.getCompanies());
    }

    @GetMapping(value = "/{id}", params = "full=true")
    public CompanyDto getCompanyById(@PathVariable long id) {
        return companyMapper.companyToDto(companyService.getCompanyById(id));
    }

    @GetMapping("/test")
    public List<CompanyDto> getTestData() {
        return companyMapper.companiesToDtos(companyService.getTestData());
    }

    @PostMapping
    public CompanyDto addCompany(@RequestBody @Valid CompanyDto companyDto) {
        return companyMapper.companyToDto(companyService.saveCompany(companyMapper.dtoToCompany(companyDto)));
    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable long id, @RequestBody @Valid CompanyDto companyDto) {
        return companyMapper.companyToDto(companyService.updateCompany(id, companyMapper.dtoToCompany(companyDto)));
    }

    @DeleteMapping
    public void deleteCompanies() {
       companyService.deleteCompanies();
    }

    @DeleteMapping("/{id}")
    public void deleteCompanyById(@PathVariable long id) {
        companyService.deleteCompanyById(id);
    }

    // --- company view endpoints ---------------------------------------------

    @GetMapping
    @JsonView(DataView.BaseDataView.class)
    public List<CompanyDto> getCompanies(@RequestParam(required = false) Boolean full) {
        return companyMapper.companiesToDtos(companyService.getCompanies());
    }

    @GetMapping("/{id}")
    @JsonView(DataView.BaseDataView.class)
    public CompanyDto getCompanyById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        return companyMapper.companyToDto(companyService.getCompanyById(id));
    }

    // --- company employee list endpoints ------------------------------------

    @PostMapping("/{companyId}/employees")
    public CompanyDto addEmployeeToCompany(
        @PathVariable long companyId, @RequestBody @Valid EmployeeDto employeeDto) {

        return companyMapper.companyToDto(
            companyService.saveEmployeeInCompany(companyId, employeeMapper.dtoToEmployee(employeeDto))
        );
    }

    @PutMapping("/{companyId}/employees")
    public CompanyDto updateEmployeeListInCompany(
        @PathVariable long companyId, @RequestBody @Valid List<EmployeeDto> employeeDtos) {

        return companyMapper.companyToDto(
            companyService.updateEmployeeListInCompany(companyId, employeeMapper.dtosToEmployees(employeeDtos))
        );
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public void deleteEmployeeInCompanyById(@PathVariable long companyId, @PathVariable long employeeId) {
        companyService.deleteEmployeeInCompanyById(companyId, employeeId);
    }
}