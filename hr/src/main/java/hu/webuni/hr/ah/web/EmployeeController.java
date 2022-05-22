package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestEmployee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    // --- attributes ---------------------------------------------------------

    private Map<Long, EmployeeDto> employeeDtos;

    // --- constructors -------------------------------------------------------

    public EmployeeController() {
        initializeTestData();
    }

    // --- getters and setters ------------------------------------------------

    public Map<Long, EmployeeDto> getEmployeeDtos() { return employeeDtos; }

    // --- public methods -----------------------------------------------------

    @GetMapping
    public List<EmployeeDto> getEmployees() {
        return employeeDtos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long id) {
        EmployeeDto employeeDto = employeeDtos.get(id);
        return employeeDto != null ? ResponseEntity.ok(employeeDto) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "salaryLimit")
    public List<EmployeeDto> getEmployeesOverSalaryLimit(@RequestParam int salaryLimit) {
        return employeeDtos.values().stream()
            .filter(employeeDto -> employeeDto.getSalary() > salaryLimit)
            .toList();
    }

    @GetMapping(params = "initTest")
    private List<EmployeeDto> initializeTestData(@RequestParam boolean initTest) {
        if (initTest) {
            initializeTestData();
        }
        return employeeDtos.values().stream().toList();
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody EmployeeDto employeeDto) {
        long id = employeeDto.getId();
        employeeDtos.put(id, employeeDto);
        return employeeDtos.get(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        if (!employeeDtos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeDtos.put(id, createEmployeeDto(id, employeeDto));
        return ResponseEntity.ok(employeeDtos.get(id));
    }

    @DeleteMapping
    public void deleteEmployees() {
        employeeDtos.clear();
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable long id) {
        employeeDtos.remove(id);
    }

    // --- private methods ----------------------------------------------------

    private void initializeTestData() {
        initializeEmployeeDtos();
        TestEmployee.initializeList().forEach(this::updateEmployeeDtos);
    }

    private void initializeEmployeeDtos() {
        if (employeeDtos == null) {
            employeeDtos = new HashMap<>();
        } else {
            employeeDtos.clear();
        }
    }

    private void updateEmployeeDtos(Employee employee) {
        employeeDtos.put(employee.getId(), createEmployeeDto(employee));
    }

    private EmployeeDto createEmployeeDto(Employee employee) {
        return new EmployeeDto(
            employee.getId(),
            employee.getName(),
            employee.getDateOfEntry(),
            employee.getPosition(),
            employee.getSalary()
        );
    }

    private EmployeeDto createEmployeeDto(long id, EmployeeDto employeeDto) {
        return new EmployeeDto(
            id,
            employeeDto.getName(),
            employeeDto.getDateOfEntry(),
            employeeDto.getPosition(),
            employeeDto.getSalary()
        );
    }
}