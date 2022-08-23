package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.CompanyTypeDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.PageResultDto;
import hu.webuni.hr.ah.mapper.CompanyMapper;
import hu.webuni.hr.ah.mapper.CompanyTypeMapper;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.service.CompanyTypeService;
import hu.webuni.hr.ah.view.CompanyDataView;
import hu.webuni.hr.ah.view.CompanyTypeDataView;
import hu.webuni.hr.ah.view.EmployeeDataView;
import hu.webuni.hr.ah.view.PageDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companyTypes")
public class CompanyTypeController {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private CompanyTypeService companyTypeService;

    @Autowired
    private CompanyTypeMapper companyTypeMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    // --- public methods -----------------------------------------------------

    @GetMapping
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public List<CompanyTypeDto> getCompanyTypes() {
        return companyTypeMapper.companyTypesToDtos(companyTypeService.getCompanyTypes());
    }

    @GetMapping(value = "/ordered", params = { "propertyName", "isAscending" })
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public List<CompanyTypeDto> getCompanyTypesOrderedByProperty(@RequestParam String propertyName,
                                                                 @RequestParam boolean isAscending) {
        return companyTypeMapper.companyTypesToDtos(
            companyTypeService.getCompanyTypesOrderedByProperty(propertyName, isAscending)
        );
    }

    @GetMapping("/paginated")
    @JsonView(PageDataView.CompanyTypeCompleteDataView.class )
    public PageResultDto<CompanyTypeDto> getCompanyTypesWithPagination(@PageableDefault(sort = "id") Pageable pageable) {
        return companyTypeMapper.pageResultToDto(companyTypeService.getCompanyTypesWithPagination(pageable));
    }

    @GetMapping("/{id}")
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto getCompanyTypeById(@PathVariable long id) {
        return companyTypeMapper.companyTypeToDto(companyTypeService.getCompanyTypeById(id));
    }

    @GetMapping("/company/{companyId}")
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto getCompanyTypeByCompanyId(@PathVariable long companyId) {
        return companyTypeMapper.companyTypeToDto(companyTypeService.getCompanyTypeByCompanyId(companyId));
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
        return companyTypeMapper.companyTypesToDtos(companyTypeService.setTestData());
    }

    @PostMapping
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto addCompanyType(@RequestBody CompanyTypeDto companyTypeDto) {
        return companyTypeMapper.companyTypeToDto(
            companyTypeService.saveCompanyType(companyTypeMapper.dtoToCompanyType(companyTypeDto))
        );
    }

    @PutMapping("/{id}")
    @JsonView(CompanyTypeDataView.CompleteDataView.class)
    public CompanyTypeDto updateCompanyType(@PathVariable long id, @RequestBody CompanyTypeDto companyTypeDto) {
        return companyTypeMapper.companyTypeToDto(
            companyTypeService.updateCompanyType(id, companyTypeMapper.dtoToCompanyType(companyTypeDto))
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