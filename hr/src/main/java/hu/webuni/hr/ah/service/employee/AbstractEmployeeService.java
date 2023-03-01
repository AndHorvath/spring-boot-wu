package hu.webuni.hr.ah.service.employee;

import hu.webuni.hr.ah.model.*;
import hu.webuni.hr.ah.model.base.PageResult;
import hu.webuni.hr.ah.model.sample.TestEmployee;
import hu.webuni.hr.ah.repository.CompanyRepository;
import hu.webuni.hr.ah.repository.CompanyTypeRepository;
import hu.webuni.hr.ah.repository.EmployeeRepository;
import hu.webuni.hr.ah.repository.PositionRepository;
import hu.webuni.hr.ah.validation.identifier.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.identifier.NonExistingIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractEmployeeService implements EmployeeService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private DataObjectIdentifierValidator identifierValidator;

    // --- public methods -----------------------------------------------------

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesOrderedBySalaryAndName() {
        return employeeRepository.findAll(Sort.by("salary").descending().and(Sort.by("name").ascending()));
    }

    public PageResult<Employee> getEmployeesWithPagination(Pageable pageable) {
        return new PageResult<>(pageable, employeeRepository.findAll(pageable));
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new NonExistingIdentifierException(id, employeeRepository));
    }

    public List<Employee> getEmployeesOverSalaryLimit(int salaryLimit) {
        return employeeRepository.findBySalaryGreaterThan(salaryLimit);
    }

    public List<Employee> getEmployeesByPosition(String positionName) {
        return employeeRepository.findByPosition(positionName);
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
        synchronizeRelatedEntitiesWithDatabase(employee);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(long idToUpdate, Employee employee) {
        validateParameter(idToUpdate);
        synchronizeRelatedEntitiesWithDatabase(employee);
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
        TestEmployee.initializeBaseList().forEach(this::saveEmployee);
    }

    private void initializeRepository() {
        if (!employeeRepository.findAll().isEmpty()) {
            employeeRepository.deleteAll();
        }
    }

    private void synchronizeRelatedEntitiesWithDatabase(Employee employee) {
        synchronizePositionWithDatabase(employee);
        synchronizeCompanyWithDatabase(employee);
    }

    private void synchronizePositionWithDatabase(Employee employee) {
        if (hasPositionWithId(employee)) {
            employee.setPosition(getPositionById(employee.getPosition().getId()));
        }
    }

    private boolean hasPositionWithId(Employee employee) {
        return employee.getPosition() != null
            && employee.getPosition().getId() != 0;
    }

    private Position getPositionById(long id) {
        return positionRepository.findById(id)
            .orElseThrow(() -> new NonExistingIdentifierException(id, positionRepository));
    }

    private void synchronizeCompanyWithDatabase(Employee employee) {
        if (hasCompanyWithId(employee)) {
            employee.setCompany(getCompanyById(employee.getCompany().getId()));
        } else if (hasCompanyTypeWithId(employee)) {
            employee.getCompany()
                .setCompanyType(getCompanyTypeById(employee.getCompany().getCompanyType().getId()));
        } else if (hasCompanyTypeWithExistingName(employee)) {
            employee.getCompany()
                .setCompanyType(getCompanyTypeByName(employee.getCompany().getCompanyType().getName()));
        }
    }

    private boolean hasCompanyWithId(Employee employee) {
        return employee.getCompany() != null
            && employee.getCompany().getId() != 0;
    }

    private boolean hasCompanyType(Employee employee) {
        return employee.getCompany() != null
            && employee.getCompany().getCompanyType() != null;
    }

    private boolean hasCompanyTypeWithId(Employee employee) {
        return hasCompanyType(employee)
            && employee.getCompany().getCompanyType().getId() != 0;
    }

    private boolean hasCompanyTypeWithExistingName(Employee employee) {
        return hasCompanyType(employee)
            && employee.getCompany().getCompanyType().getName() != null
            && !employee.getCompany().getCompanyType().getName().isEmpty()
            && getCompanyTypeByName(employee.getCompany().getCompanyType().getName()) != null;
    }

    private Company getCompanyById(long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new NonExistingIdentifierException(id, companyRepository));
    }

    private CompanyType getCompanyTypeById(long id) {
        return companyTypeRepository.findById(id)
            .orElseThrow(() -> new NonExistingIdentifierException(id, companyTypeRepository));
    }

    private CompanyType getCompanyTypeByName(String name) {
        return companyTypeRepository.findByName(name);
    }
}