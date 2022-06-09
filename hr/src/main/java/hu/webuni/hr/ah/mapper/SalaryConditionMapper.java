package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.SalaryConditionDto;
import hu.webuni.hr.ah.model.SalaryCondition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalaryConditionMapper {

    SalaryConditionDto salaryConditionToDto(SalaryCondition salaryCondition);
}