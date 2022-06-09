package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestEmployee;
import hu.webuni.hr.ah.validation.DataObjectIdentifierValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractEmployeeService implements EmployeeService {

    // --- attributes ---------------------------------------------------------

    private Map<Long, Employee> employees;

    @Autowired
    private DataObjectIdentifierValidator identifierValidator;

    // --- constructors -------------------------------------------------------

    public AbstractEmployeeService() {
        employees = new TreeMap<>();
    }

    // --- public methods -----------------------------------------------------

    public List<Employee> getEmployees() {
        return getEmployeeList();
    }

    public Employee getEmployeeById(long id) {
        validateParameter(id);
        return employees.get(id);
    }

    public List<Employee> getEmployeesOverSalaryLimit(int salaryLimit) {
        return employees.values().stream()
            .filter(employee -> employee.getSalary() > salaryLimit)
            .toList();
    }

    public List<Employee> getTestData() {
        initializeTestData();
        return getEmployeeList();
    }

    public Employee saveEmployee(Employee employee) {
        long id = employee.getId();
        employees.put(id, employee);
        return employees.get(id);
    }

    public Employee updateEmployee(long id, Employee employee) {
        validateParameter(id);
        employees.put(id, createEmployee(id, employee));
        return employees.get(id);
    }

    public void deleteEmployees() {
        employees.clear();
    }

    public void deleteEmployeeById(long id) {
        employees.remove(id);
    }

    // --- private methods ----------------------------------------------------

    private List<Employee> getEmployeeList() {
        return employees.values().stream().toList();
    }

    private void validateParameter(long id) {
        identifierValidator.validateIdentifierExistence(employees, id);
    }

    private void initializeTestData() {
        initializeEmployees();
        TestEmployee.initializeList().forEach(this::updateEmployees);
    }

    private void initializeEmployees() {
        if (!employees.isEmpty()) {
            employees.clear();
        }
    }

    private void updateEmployees(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    private Employee createEmployee(long id, Employee employee) {
        return new Employee(
            id,
            employee.getName(),
            employee.getDateOfEntry(),
            employee.getPosition(),
            employee.getSalary()
        );
    }
}