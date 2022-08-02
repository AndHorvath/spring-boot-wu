package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestCompany;
import hu.webuni.hr.ah.repository.CompanyRepository;
import hu.webuni.hr.ah.validation.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.NonExistingIdentifierException;
import hu.webuni.hr.ah.validation.NonUniqueIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DataObjectIdentifierValidator identifierValidator;

    // --- public methods -----------------------------------------------------

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
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
        companyRepository.deleteAll();
    }

    @Transactional
    public void deleteCompanyById(long id) {
        validateParameter(id);
        companyRepository.deleteById(id);
    }

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
        Optional<Company> companyOfSameRegistrationNumber = companyRepository.findAll().stream()
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
        if (!companyRepository.findAll().isEmpty()) {
            companyRepository.deleteAll();
        }
    }

    private Company prepareCompanyForSave(Company company) {
        company.getEmployees().forEach(employee -> employee.setCompany(company));
        return company;
    }

    private Company prepareCompanyForSave(long idToUpdate, Company company) {
        Company companyWithId = company.createCopyWithId(idToUpdate);
        companyWithId.getEmployees().forEach(employee -> employee.setCompany(companyWithId));
        return companyWithId;
    }

    private Company prepareCompanyForSave(long companyId, Employee employee) {
        Company companyToUpdate = getCompanyById(companyId);
        companyToUpdate.addEmployee(employee);
        return companyToUpdate;
    }

    private Company prepareCompanyForSave(long companyId, List<Employee> employees) {
        Company companyToUpdate = getCompanyById(companyId);
        companyToUpdate.clearEmployeeList();
        companyToUpdate.addEmployees(employees);
        return companyToUpdate;
    }

    private Company prepareCompanyForSave(long companyId, long employeeId) {
        Company companyToUpdate = getCompanyById(companyId);
        companyToUpdate.removeEmployeeById(employeeId);
        return companyToUpdate;
    }
}