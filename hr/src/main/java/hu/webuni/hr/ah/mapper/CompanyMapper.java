package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.CompanyTypeDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.CompanyType;
import hu.webuni.hr.ah.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    // --- company mappings ---------------------------------------------------

    CompanyDto companyToDto(Company company);
    Company dtoToCompany(CompanyDto companyDto);

    List<CompanyDto> companiesToDtos(List<Company> companies);

    // --- company type mappings ----------------------------------------------

    CompanyTypeDto companyTypeToDto(CompanyType companyType);
    CompanyType dtoToCompanyType(CompanyTypeDto companyTypeDto);

    List<CompanyTypeDto> companyTypesToDtos(List<CompanyType> companyTypes);

    // --- helper mappings ----------------------------------------------------

    @Mapping(target = "company", ignore = true)
    EmployeeDto employeeToDto(Employee employee);
}