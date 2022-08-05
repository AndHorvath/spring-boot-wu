package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.CompanySalaryConditionDto;
import hu.webuni.hr.ah.dto.EmployeeSalaryConditionDto;
import hu.webuni.hr.ah.dto.PositionSalaryConditionDto;
import hu.webuni.hr.ah.model.CompanySalaryCondition;
import hu.webuni.hr.ah.model.EmployeeSalaryCondition;
import hu.webuni.hr.ah.model.PositionSalaryCondition;
import org.mapstruct.Mapper;

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
}