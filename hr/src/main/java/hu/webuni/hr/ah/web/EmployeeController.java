package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.service.AbstractEmployeeService;
import hu.webuni.hr.ah.view.EmployeeDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    @JsonView(EmployeeDataView.DetailedDataView.class)
    public List<EmployeeDto> getEmployees() {
        return employeeMapper.employeesToDtos(employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    @JsonView(EmployeeDataView.CompleteDataView.class)
    public EmployeeDto getEmployeeById(@PathVariable long id) {
        return employeeMapper.employeeToDto(employeeService.getEmployeeById(id));
    }

    @GetMapping(params = "salaryLimit")
    @JsonView(EmployeeDataView.DetailedDataView.class)
    public List<EmployeeDto> getEmployeesOverSalaryLimit(@RequestParam int salaryLimit) {
        return employeeMapper.employeesToDtos(employeeService.getEmployeesOverSalaryLimit(salaryLimit));
    }

    @GetMapping(params = "position")
    @JsonView(EmployeeDataView.DetailedDataView.class)
    public List<EmployeeDto> getEmployeesByPosition(@RequestParam String position) {
        return employeeMapper.employeesToDtos(employeeService.getEmployeesByPosition(position));
    }

    @GetMapping(params = "nameStart")
    @JsonView(EmployeeDataView.DetailedDataView.class)
    public List<EmployeeDto> getEmployeesByNameStart(@RequestParam String nameStart) {
        return employeeMapper.employeesToDtos(employeeService.getEmployeesByNameStart(nameStart));
    }

    @GetMapping(params = {"lowerDateLimit", "upperDateLimit"})
    @JsonView(EmployeeDataView.DetailedDataView.class)
    public List<EmployeeDto> getEmployeesByDateOfEntry(@RequestParam LocalDateTime lowerDateLimit,
                                                       @RequestParam LocalDateTime upperDateLimit) {
        return employeeMapper.employeesToDtos(
            employeeService.getEmployeesByDateOfEntry(lowerDateLimit, upperDateLimit)
        );
    }

    @GetMapping("/test")
    @JsonView(EmployeeDataView.CompleteDataView.class)
    public List<EmployeeDto> getTestData() {
        return employeeMapper.employeesToDtos(employeeService.setTestData());
    }

    @PostMapping
    @JsonView(EmployeeDataView.CompleteDataView.class)
    public EmployeeDto addEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return employeeMapper.employeeToDto(employeeService.saveEmployee(employeeMapper.dtoToEmployee(employeeDto)));
    }

    @PutMapping("/{id}")
    @JsonView(EmployeeDataView.CompleteDataView.class)
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