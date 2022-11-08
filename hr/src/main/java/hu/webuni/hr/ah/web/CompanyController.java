package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.PageResultDto;
import hu.webuni.hr.ah.mapper.CompanyMapper;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.view.CompanyDataView;
import hu.webuni.hr.ah.service.CompanyService;
import hu.webuni.hr.ah.view.PageDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @JsonView(CompanyDataView.DetailedDataView.class)
    public List<CompanyDto> getCompanies() {
        return companyMapper.companiesToDtos(companyService.getCompanies());
    }

    @GetMapping(value = "/ordered", params = { "propertyName", "isAscending" })
    @JsonView(CompanyDataView.DetailedDataView.class)
    public List<CompanyDto> getCompaniesOrderedByProperty(@RequestParam String propertyName,
                                                          @RequestParam boolean isAscending) {
        return companyMapper.companiesToDtos(companyService.getCompaniesOrderedByProperty(propertyName, isAscending));
    }

    @GetMapping("/paginated")
    @JsonView(PageDataView.CompanyCompleteDataView.class)
    public PageResultDto<CompanyDto> getCompaniesWithPagination(@PageableDefault(sort = "id") Pageable pageable) {
        return companyMapper.pageResultToDto(companyService.getCompaniesWithPagination(pageable));
    }

    @GetMapping(value = "/{id}", params = "full=true")
    @JsonView(CompanyDataView.CompleteDataView.class)
    public CompanyDto getCompanyById(@PathVariable long id) {
        return companyMapper.companyToDto(companyService.getCompanyById(id));
    }

    @GetMapping(params = "salaryLimit")
    @JsonView(CompanyDataView.CompleteDataView.class)
    public List<CompanyDto> getCompaniesWithEmployeesOverSalaryLimit(@RequestParam int salaryLimit) {
        return companyMapper.companiesToDtos(companyService.getCompaniesWithEmployeesOverSalaryLimit(salaryLimit));
    }

    @GetMapping(params = "employeeLimit")
    @JsonView(CompanyDataView.DetailedDataView.class)
    public List<CompanyDto> getCompaniesWithEmployeesOverEmployeeLimit(@RequestParam long employeeLimit) {
        return companyMapper.companiesToDtos(companyService.getCompaniesWithEmployeesOverEmployeeLimit(employeeLimit));
    }

    @GetMapping(params = "positionName")
    @JsonView(CompanyDataView.CompleteDataView.class)
    public List<CompanyDto> getCompaniesWithPosition(@RequestParam String positionName) {
        return companyMapper.companiesToDtos(companyService.getCompaniesWithPosition(positionName));
    }

    @GetMapping("/test")
    @JsonView(CompanyDataView.CompleteDataView.class)
    public List<CompanyDto> getTestData() {
        return companyMapper.companiesToDtos(companyService.setTestData());
    }

    @PostMapping
    @JsonView(CompanyDataView.CompleteDataView.class)
    public CompanyDto addCompany(@RequestBody @Valid CompanyDto companyDto) {
        return companyMapper.companyToDto(companyService.saveCompany(companyMapper.dtoToCompany(companyDto)));
    }

    @PutMapping("/{id}")
    @JsonView(CompanyDataView.CompleteDataView.class)
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

    // --- company base view endpoints ----------------------------------------

    @GetMapping
    @JsonView(CompanyDataView.BaseDataView.class)
    public List<CompanyDto> getCompanies(@RequestParam(required = false) Boolean full) {
        return companyMapper.companiesToDtos(companyService.getCompanies());
    }

    @GetMapping("/{id}")
    @JsonView(CompanyDataView.BaseDataView.class)
    public CompanyDto getCompanyById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        return companyMapper.companyToDto(companyService.getCompanyById(id));
    }

    // --- company employee list endpoints ------------------------------------

    @PostMapping("/{companyId}/employees")
    @JsonView(CompanyDataView.CompleteDataView.class)
    public CompanyDto addEmployeeToCompany(@PathVariable long companyId, @RequestBody @Valid EmployeeDto employeeDto) {
        return companyMapper.companyToDto(
            companyService.saveEmployeeInCompany(companyId, employeeMapper.dtoToEmployee(employeeDto))
        );
    }

    @PutMapping("/{companyId}/employees")
    @JsonView(CompanyDataView.CompleteDataView.class)
    public CompanyDto updateEmployeeListInCompany(@PathVariable long companyId,
                                                  @RequestBody @Valid List<EmployeeDto> employeeDtos) {
        return companyMapper.companyToDto(
            companyService.updateEmployeeListInCompany(companyId, employeeMapper.dtosToEmployees(employeeDtos))
        );
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    @JsonView(CompanyDataView.DetailedDataView.class)
    public CompanyDto deleteEmployeeInCompanyById(@PathVariable long companyId, @PathVariable long employeeId) {
        return companyMapper.companyToDto(companyService.deleteEmployeeInCompanyById(companyId, employeeId));
    }
}