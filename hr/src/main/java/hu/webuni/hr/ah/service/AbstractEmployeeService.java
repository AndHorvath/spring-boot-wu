package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestEmployee;
import hu.webuni.hr.ah.repository.CompanyRepository;
import hu.webuni.hr.ah.repository.EmployeeRepository;
import hu.webuni.hr.ah.validation.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.NonExistingIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractEmployeeService implements EmployeeService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

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

    public List<Employee> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }

    public List<Employee> getEmployeesByNameStart(String nameStart) {
        return employeeRepository.findByNameStartingWithIgnoreCase(nameStart);
    }

    public List<Employee> getEmployeesByDateOfEntry(LocalDateTime lowerDateLimit, LocalDateTime upperDateLimit) {
        return employeeRepository.findByDateOfEntryBetween(lowerDateLimit, upperDateLimit);
    }

    @Transactional
    public List<Employee> setTestData() {
        initializeTestData();
        return getEmployees();
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        synchronizeCompanyWithDatabase(employee);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(long idToUpdate, Employee employee) {
        validateParameter(idToUpdate);
        synchronizeCompanyWithDatabase(employee);
        return employeeRepository.save(employee.createCopyWithId(idToUpdate));
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
        TestEmployee.initializeList().forEach(this::saveEmployee);
    }

    private void initializeRepository() {
        if (!employeeRepository.findAll().isEmpty()) {
            employeeRepository.deleteAll();
        }
    }

    private void synchronizeCompanyWithDatabase(Employee employee) {
        if (hasCompanyWithId(employee)) {
            employee.setCompany(getCompanyById(employee.getCompany().getId()));
        }
    }

    private boolean hasCompanyWithId(Employee employee) {
        return employee.getCompany() != null && employee.getCompany().getId() != 0;
    }

    private Company getCompanyById(long id) {
        return companyRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
    }
}