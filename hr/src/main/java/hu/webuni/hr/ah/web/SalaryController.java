package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.SalaryConditionDto;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.mapper.SalaryConditionMapper;
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

    // --- public methods -----------------------------------------------------

    @GetMapping("/{id}")
    public SalaryConditionDto getSalaryResultById(@PathVariable long id) {
        return salaryConditionMapper.salaryConditionToDto(salaryService.getEmployeesSalaryConditionById(id));
    }

    @PutMapping
    public SalaryConditionDto getSalaryResult(@RequestBody @Valid EmployeeDto employeeDto) {
        return salaryConditionMapper.salaryConditionToDto(
            salaryService.getEmployeesSalaryCondition(employeeMapper.dtoToEmployee(employeeDto))
        );
    }
}