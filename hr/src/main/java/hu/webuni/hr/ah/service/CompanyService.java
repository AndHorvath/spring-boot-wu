package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestCompany;
import hu.webuni.hr.ah.validation.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.NonUniqueIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class CompanyService {

    // --- attributes ---------------------------------------------------------

    private Map<Long , Company> companies;

    @Autowired
    private DataObjectIdentifierValidator identifierValidator;

    // --- constructors -------------------------------------------------------

    public CompanyService() {
        companies = new TreeMap<>();
    }

    // --- public methods -----------------------------------------------------

    public List<Company> getCompanies() {
        return getCompanyList();
    }

    public Company getCompanyById(long id) {
        validateParameter(id);
        return companies.get(id);
    }

    public List<Company> getTestData() {
        initializeTestData();
        return getCompanyList();
    }

    public Company saveCompany(Company company) {
        long id = company.getId();
        companies.put(id, company);
        return companies.get(id);
    }

    public Company updateCompany(long id, Company company) {
        validateParameter(id, company);
        companies.put(id, createCompany(id, company));
        return companies.get(id);
    }

    public void deleteCompanies() {
        companies.clear();
    }

    public void deleteCompanyById(long id) {
        companies.remove(id);
    }

    public Company saveEmployeeInCompany(long companyId, Employee employee) {
        validateParameter(companyId);
        companies.get(companyId).addEmployee(employee);
        return companies.get(companyId);
    }

    public Company updateEmployeeListInCompany(long companyId, List<Employee> employees) {
        validateParameter(companyId);
        companies.get(companyId).setEmployees(employees);
        return companies.get(companyId);
    }

    public void deleteEmployeeInCompanyById(long companyId, long employeeId) {
        validateParameter(companyId);
        companies.get(companyId).removeEmployeeById(employeeId);
    }

    // --- private methods ----------------------------------------------------

    private List<Company> getCompanyList() {
        return companies.values().stream().toList();
    }

    private void validateParameter(long id, Company company) {
        validateParameter(id);
        validateParameter(company);
    }

    private void validateParameter(long id) {
        identifierValidator.validateIdentifierExistence(companies, id);
    }

    private void validateParameter(Company company) {
        Optional<Company> companyOfSameRegistrationNumber = companies.values().stream()
            .filter(savedCompany -> isOfSameRegistrationNumber(savedCompany, company))
            .findAny();
        if (companyOfSameRegistrationNumber.isPresent()) {
            throw new NonUniqueIdentifierException(company);
        }
    }

    private boolean isOfSameRegistrationNumber(Company company, Company other) {
        return company.getRegistrationNumber().equals(other.getRegistrationNumber()) && isNotUpdate(company, other);
    }

    private boolean isNotUpdate(Company company, Company other) {
        return company.getId() != other.getId();
    }

    private void initializeTestData() {
        initializeCompanies();
        TestCompany.initializeList().forEach(this::updateCompanies);
    }

    private void initializeCompanies() {
        if (!companies.isEmpty()) {
            companies.clear();
        }
    }

    private void updateCompanies(Company company) {
        companies.put(company.getId(), company);
    }

    private Company createCompany(long id, Company company) {
        return new Company(
            id,
            company.getRegistrationNumber(),
            company.getName(),
            company.getAddress(),
            company.getEmployees()
        );
    }
}