package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.*;
import hu.webuni.hr.ah.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaryConditionMapper {

    // --- employee salary condition mappings ---------------------------------

    EmployeeSalaryConditionDto employeeSalaryConditionToDto(EmployeeSalaryCondition employeeSalaryCondition);

    // --- company salary condition mappings ----------------------------------

    CompanySalaryConditionDto companySalaryConditionToDto(CompanySalaryCondition companySalaryCondition);

    // --- position salary condition mappings ---------------------------------

    PositionSalaryConditionDto positionSalaryConditionToDto(PositionSalaryCondition positionSalaryCondition);

    List<PositionSalaryConditionDto> positionSalaryConditionsToDtos(List<PositionSalaryCondition> positionSalaryConditions);

    // --- helper mappings ----------------------------------------------------

    @Mapping(target = "employees", ignore = true)
    CompanyDto companyToDto(Company company);
}