package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.base.PageResultDto;
import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.base.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto employeeToDto(Employee employee);
    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> employeesToDtos(List<Employee> employees);
    List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);

    PageResultDto<EmployeeDto> pageResultToDto(PageResult<Employee> pageResult);

    // --- helper mappings ----------------------------------------------------

    @Mapping(target = "employees", ignore = true)
    CompanyDto companyToDto(Company company);
}