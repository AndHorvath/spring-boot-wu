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

    EmployeeSalaryConditionDto employeeSalaryConditionToDto(EmployeeSalaryCondition employeeSalaryCondition);

    CompanySalaryConditionDto companySalaryConditionToDto(CompanySalaryCondition companySalaryCondition);

    PositionSalaryConditionDto positionSalaryConditionToDto(PositionSalaryCondition positionSalaryCondition);

    List<PositionSalaryConditionDto> positionSalaryConditionsToDtos(List<PositionSalaryCondition> positionSalaryConditions);
}