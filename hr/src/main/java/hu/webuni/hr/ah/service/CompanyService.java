package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.*;
import hu.webuni.hr.ah.model.sample.TestCompany;
import hu.webuni.hr.ah.repository.CompanyRepository;
import hu.webuni.hr.ah.repository.CompanyTypeRepository;
import hu.webuni.hr.ah.repository.EmployeeRepository;
import hu.webuni.hr.ah.validation.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.NonExistingIdentifierException;
import hu.webuni.hr.ah.validation.NonUniqueIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DataObjectIdentifierValidator identifierValidator;

    // --- simple service methods ---------------------------------------------

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompaniesOrderedByProperty(String propertyName, boolean isAscending) {
        return isAscending ?
            companyRepository.findAll(Sort.by(propertyName).ascending()) :
            companyRepository.findAll(Sort.by(propertyName).descending());
    }

    public PageResult<Company> getCompaniesWithPagination(Pageable pageable) {
        return new PageResult<>(pageable, companyRepository.findAll(pageable));
    }

    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
    }

    public List<Company> getCompaniesWithEmployeesOverSalaryLimit(int salaryLimit) {
        return companyRepository.findDistinctByEmployeesSalaryGreaterThan(salaryLimit);
    }

    public List<Company> getCompaniesWithEmployeesOverLimit(long employeeLimit) {
        return companyRepository.findDistinctByNumberOfEmployeesGreaterThan(employeeLimit);
    }

    public List<Object[]> getAverageSalariesOfPositionsByCompanyId(long companyId) {
        validateParameter(companyId);
        return companyRepository.findAverageSalariesOfPositionsByCompanyId(companyId);
    }

    @Transactional
    public List<Company> setTestData() {
        initializeTestData();
        return getCompanies();
    }

    @Transactional
    public Company saveCompany(Company company) {
        validateParameter(company);
        return companyRepository.save(prepareCompanyForSave(company));
    }

    @Transactional
    public Company updateCompany(long idToUpdate, Company company) {
        validateParameters(idToUpdate, company);
        return companyRepository.save(prepareCompanyForSave(idToUpdate, company));
    }

    @Transactional
    public void deleteCompanies() {
        getCompanies().forEach(this::prepareCompanyForRemove);
        companyRepository.deleteAll();
    }

    @Transactional
    public void deleteCompanyById(long id) {
        prepareCompanyForRemove(id);
        companyRepository.deleteById(id);
    }

    // --- service methods manipulating employee list -------------------------

    @Transactional
    public Company saveEmployeeInCompany(long companyId, Employee employee) {
        return companyRepository.save(prepareCompanyForSave(companyId, employee));
    }

    @Transactional
    public Company updateEmployeeListInCompany(long companyId, List<Employee> employees) {
        return companyRepository.save(prepareCompanyForSave(companyId, employees));
    }

    @Transactional
    public Company deleteEmployeeInCompanyById(long companyId, long employeeId) {
        return companyRepository.save(prepareCompanyForSave(companyId, employeeId));
    }

    // --- private methods ----------------------------------------------------

    private void validateParameters(long id, Company company) {
        validateParameter(id);
        validateParameter(id, company);
    }

    private void validateParameter(long id) {
        identifierValidator.validateIdentifierExistence(companyRepository, id);
    }

    private void validateParameter(Company company) {
        validateParameter(company.getId(), company);
    }

    private void validateParameter(long idToUpdate, Company company) {
        Optional<Company> companyOfSameRegistrationNumber = getCompanies().stream()
            .filter(savedCompany -> isOfSameRegistrationNumber(savedCompany, company, idToUpdate))
            .findAny();
        if (companyOfSameRegistrationNumber.isPresent()) {
            throw new NonUniqueIdentifierException(company);
        }
    }

    private boolean isOfSameRegistrationNumber(Company savedCompany, Company company, long idToUpdate) {
        return savedCompany.getRegistrationNumber().equals(company.getRegistrationNumber())
            && isNotUpdate(savedCompany, idToUpdate);
    }

    private boolean isNotUpdate(Company savedCompany, long idToUpdate) {
        return savedCompany.getId() != idToUpdate;
    }

    private void initializeTestData() {
        initializeRepository();
        TestCompany.initializeList().forEach(this::saveCompany);
    }

    private void initializeRepository() {
        if (!getCompanies().isEmpty()) {
            deleteCompanies();
        }
    }

    private Company prepareCompanyForSave(Company company) {
        synchronizeCompanyTypeWithDatabase(company);

        company.getEmployees().forEach(employee -> synchronizeEmployeeWithDatabase(company, employee));
        company.getEmployees().forEach(employee -> employee.setCompany(company));
        return company;
    }

    private Company prepareCompanyForSave(long idToUpdate, Company company) {
        synchronizeCompanyTypeWithDatabase(company);
        
        Company savedCompany = getCompanyById(idToUpdate);
        savedCompany.clearEmployeeList();

        Company companyWithId = company.createCopyWithId(idToUpdate);
        companyWithId.getEmployees().forEach(employee -> synchronizeEmployeeWithDatabase(companyWithId, employee));
        companyWithId.getEmployees().forEach(employee -> employee.setCompany(companyWithId));
        return companyWithId;
    }

    private Company prepareCompanyForSave(long companyId, Employee employee) {
        Company companyToUpdate = getCompanyById(companyId);
        synchronizeDatabaseWithEmployee(employee);
        companyToUpdate.addEmployee(employee);
        return companyToUpdate;
    }

    private Company prepareCompanyForSave(long companyId, List<Employee> employees) {
        Company companyToUpdate = getCompanyById(companyId);
        prepareCompanyForRemoveEmployments(companyToUpdate);
        companyToUpdate.clearEmployeeList();

        employees.forEach(this::synchronizeDatabaseWithEmployee);
        companyToUpdate.addEmployees(employees);
        return companyToUpdate;
    }

    private Company prepareCompanyForSave(long companyId, long employeeId) {
        Employee employeeToRemove = getEmployeeById(employeeId);
        employeeToRemove.deleteEmployment();

        Company companyToUpdate = getCompanyById(companyId);
        companyToUpdate.removeEmployee(employeeToRemove);
        return companyToUpdate;
    }

    private void prepareCompanyForRemove(long id) {
        prepareCompanyForRemove(getCompanyById(id));
    }

    private void prepareCompanyForRemove(Company company) {
        prepareCompanyForRemoveEmployments(company);
    }

    private void prepareCompanyForRemoveEmployments(Company company) {
        company.getEmployees().forEach(Employee::deleteEmployment);
    }

    private void synchronizeCompanyTypeWithDatabase(Company company) {
        if (hasCompanyTypeWithId(company)) {
            company.setCompanyType(getCompanyTypeById(company.getCompanyType().getId()));
        } else if (hasCompanyTypeWithExistingName(company)) {
            company.setCompanyType(getCompanyTypeByName(company.getCompanyType().getName()));
        }
    }

    private void synchronizeEmployeeWithDatabase(Company company, Employee employee) {
        List<Employee> employees = company.getEmployees();
        int employeeIndex = employees.indexOf(employee);
        long employeeId = employee.getId();
        if (employeeId != 0) {
            employees.set(employeeIndex, getEmployeeById(employeeId));
        }
    }

    private void synchronizeDatabaseWithEmployee(Employee employee) {
        long employeeId = employee.getId();
        if (employeeId != 0) {
            Employee savedEmployee = getEmployeeById(employeeId);
            savedEmployee.setName(employee.getName());
            savedEmployee.setDateOfEntry(employee.getDateOfEntry());
            savedEmployee.setPosition(employee.getPosition());
            savedEmployee.setSalary(employee.getSalary());
        }
    }

    private boolean hasCompanyTypeWithId(Company company) {
        return company.getCompanyType() != null
            && company.getCompanyType().getId() != 0;
    }

    private boolean hasCompanyTypeWithExistingName(Company company) {
        return company.getCompanyType() != null
            && company.getCompanyType().getName() != null
            && !company.getCompanyType().getName().isEmpty()
            && getCompanyTypeByName(company.getCompanyType().getName()) != null;
    }

    private CompanyType getCompanyTypeById(long id) {
        return companyTypeRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
    }

    private CompanyType getCompanyTypeByName(String name) {
        return companyTypeRepository.findByName(name);
    }

    private Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
    }
}