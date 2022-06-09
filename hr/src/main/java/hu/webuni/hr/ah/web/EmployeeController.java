package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.service.AbstractEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private AbstractEmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    // --- public methods -----------------------------------------------------

    @GetMapping
    public List<EmployeeDto> getEmployees() {
        return employeeMapper.employeesToDtos(employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id) {
        return employeeMapper.employeeToDto(employeeService.getEmployeeById(id));
    }

    @GetMapping(params = "salaryLimit")
    public List<EmployeeDto> getEmployeesOverSalaryLimit(@RequestParam int salaryLimit) {
        return employeeMapper.employeesToDtos(employeeService.getEmployeesOverSalaryLimit(salaryLimit));
    }

    @GetMapping("/test")
    public List<EmployeeDto> getTestData() {
        return employeeMapper.employeesToDtos(employeeService.getTestData());
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return employeeMapper.employeeToDto(
            employeeService.saveEmployee(employeeMapper.dtoToEmployee(employeeDto))
        );
    }

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        return employeeMapper.employeeToDto(
            employeeService.updateEmployee(id, employeeMapper.dtoToEmployee(employeeDto))
        );
    }

    @DeleteMapping
    public void deleteEmployees() {
        employeeService.deleteEmployees();
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
    }
}