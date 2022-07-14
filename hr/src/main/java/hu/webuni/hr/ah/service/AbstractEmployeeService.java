package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestEmployee;
import hu.webuni.hr.ah.repository.EmployeeRepository;
import hu.webuni.hr.ah.validation.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.NonExistingIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractEmployeeService implements EmployeeService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DataObjectIdentifierValidator identifierValidator;

    // --- public methods -----------------------------------------------------

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
    }

    public List<Employee> getEmployeesOverSalaryLimit(int salaryLimit) {
        return employeeRepository.findBySalaryGreaterThan(salaryLimit);
    }

    @Transactional
    public List<Employee> getTestData() {
        initializeTestData();
        return getEmployees();
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(long id, Employee employee) {
        validateParameter(id);
        return employeeRepository.save(createEmployee(id, employee));
    }

    @Transactional
    public void deleteEmployees() {
        employeeRepository.deleteAll();
    }

    @Transactional
    public void deleteEmployeeById(long id) {
        validateParameter(id);
        employeeRepository.deleteById(id);
    }

    // --- private methods ----------------------------------------------------

    private void validateParameter(long id) {
        identifierValidator.validateIdentifierExistence(employeeRepository, id);
    }

    private void initializeTestData() {
        initializeRepository();
        TestEmployee.initializeList().forEach(this::updateRepository);
    }

    private void initializeRepository() {
        if (!employeeRepository.findAll().isEmpty()) {
            employeeRepository.deleteAll();
        }
    }

    private void updateRepository(Employee employee) {
        employeeRepository.save(employee);
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