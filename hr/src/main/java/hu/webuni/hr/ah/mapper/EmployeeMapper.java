package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto employeeToDto(Employee employee);

    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);
}