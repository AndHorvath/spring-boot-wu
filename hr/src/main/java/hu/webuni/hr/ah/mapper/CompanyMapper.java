package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.PageResultDto;
import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto companyToDto(Company company);
    Company dtoToCompany(CompanyDto companyDto);

    List<CompanyDto> companiesToDtos(List<Company> companies);

    PageResultDto<CompanyDto> pageResultToDto(PageResult<Company> pageResult);

    // --- helper mappings ----------------------------------------------------

    @Mapping(target = "company", ignore = true)
    EmployeeDto employeeToDto(Employee employee);
}