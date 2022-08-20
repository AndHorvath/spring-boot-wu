package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.CompanyTypeDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.mapper.CompanyMapper;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.service.CompanyTypeService;
import hu.webuni.hr.ah.view.CompanyDataView;
import hu.webuni.hr.ah.view.CompanyTypeDataView;
import hu.webuni.hr.ah.view.EmployeeDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companyTypes")
public class CompanyTypeController {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private CompanyTypeService companyTypeService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    // --- public methods -----------------------------------------------------

    @GetMapping
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public List<CompanyTypeDto> getCompanyTypes() {
        return companyMapper.companyTypesToDtos(companyTypeService.getCompanyTypes());
    }

    @GetMapping("/{id}")
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto getCompanyTypeById(@PathVariable long id) {
        return companyMapper.companyTypeToDto(companyTypeService.getCompanyTypeById(id));
    }

    @GetMapping("/company/{companyId}")
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto getCompanyTypeByCompanyId(@PathVariable long companyId) {
        return companyMapper.companyTypeToDto(companyTypeService.getCompanyTypeByCompanyId(companyId));
    }

    @GetMapping(value = "/company", params = "companyTypeName")
    @JsonView(CompanyDataView.BaseDataView.class)
    public List<CompanyDto> getCompaniesByCompanyType(@RequestParam String companyTypeName) {
        return companyMapper.companiesToDtos(companyTypeService.getCompaniesByCompanyType(companyTypeName));
    }

    @GetMapping(value = "/employee", params = "companyTypeName")
    @JsonView(EmployeeDataView.CompleteDataView.class)
    public List<EmployeeDto> getEmployeesByCompanyType(@RequestParam String companyTypeName) {
        return employeeMapper.employeesToDtos(companyTypeService.getEmployeesByCompanyType(companyTypeName));
    }

    @GetMapping("/test")
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public List<CompanyTypeDto> getTestData() {
        return companyMapper.companyTypesToDtos(companyTypeService.setTestData());
    }

    @PostMapping
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto addCompanyType(@RequestBody CompanyTypeDto companyTypeDto) {
        return companyMapper.companyTypeToDto(
            companyTypeService.saveCompanyType(companyMapper.dtoToCompanyType(companyTypeDto))
        );
    }

    @PutMapping("/{id}")
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto updateCompanyType(@PathVariable long id, @RequestBody CompanyTypeDto companyTypeDto) {
        return companyMapper.companyTypeToDto(
            companyTypeService.updateCompanyType(id, companyMapper.dtoToCompanyType(companyTypeDto))
        );
    }

    @DeleteMapping
    public void deleteCompanyTypes() {
        companyTypeService.deleteCompanyTypes();
    }

    @DeleteMapping("/{id}")
    public void deleteCompanyTypeById(@PathVariable long id) {
        companyTypeService.deleteCompanyTypeById(id);
    }
}