package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.CompanySalaryConditionDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.EmployeeSalaryConditionDto;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.mapper.SalaryConditionMapper;
import hu.webuni.hr.ah.model.DataView;
import hu.webuni.hr.ah.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SalaryConditionMapper salaryConditionMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    // --- employee salary endpoints ------------------------------------------

    @PutMapping("/employee")
    public EmployeeSalaryConditionDto getEmployeeSalaryResult(@RequestBody @Valid EmployeeDto employeeDto) {
        return salaryConditionMapper.employeeSalaryConditionToDto(
            salaryService.getEmployeesSalaryCondition(employeeMapper.dtoToEmployee(employeeDto))
        );
    }

    @GetMapping("/employee/{id}")
    public EmployeeSalaryConditionDto getEmployeeSalaryResultById(@PathVariable long id) {
        return salaryConditionMapper.employeeSalaryConditionToDto(salaryService.getEmployeesSalaryConditionById(id));
    }

    // --- company salary endpoints -------------------------------------------

    @GetMapping("/company/{id}")
    @JsonView(DataView.BaseDataView.class)
    public CompanySalaryConditionDto getCompanySalaryResultById(@PathVariable long id) {
        return salaryConditionMapper.companySalaryConditionToDto(salaryService.getCompanySalaryConditionById(id));
    }
}