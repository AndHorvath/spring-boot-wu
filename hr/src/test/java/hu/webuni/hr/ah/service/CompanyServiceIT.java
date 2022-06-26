package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.validation.NonExistingIdentifierException;
import hu.webuni.hr.ah.validation.NonUniqueIdentifierException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CompanyServiceIT {

    @Autowired
    CompanyService companyService;

    Company company;
    Company companyOfSameRegistrationNumber;

    Employee employee;

    Employee dummyEmployee;
    Company dummyCompany;

    Company returnValue;
    List<Company> returnValueList;

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000);

        company = new Company(1, "AA-11", "Company", "Address", List.of(employee));

        dummyEmployee = new Employee(0, "D", LocalDateTime.of(1, 1, 1, 1, 1), "D", 1);
        dummyCompany = new Company(0, "DDDDD", "D", "D", List.of(dummyEmployee));
    }

    @Test
    void testGetCompanies() {
        clearAndSaveDummyCompanyForId(0);

        returnValueList = companyService.getCompanies();
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testGetCompanyById() {
        clearAndSaveDummyCompanyForId(0);

        returnValue = companyService.getCompanyById(0);
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(dummyCompany);
    }

    @Test
    void testGetCompanyById_InvalidId() {
        clearAndSaveDummyCompanyForId(1);

        assertThatThrownBy(() -> companyService.getCompanyById(0))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in memory: 0");
    }

    @Test
    void testSaveCompany() {
        clearAndSaveDummyCompanyForId(0);

        returnValue = companyService.saveCompany(company);
        returnValueList = companyService.getCompanies();
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(company);
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, company));
    }

    @Test
    void testSaveCompany_ExistingId() {
        clearAndSaveDummyCompanyForId(1);

        returnValue = companyService.saveCompany(company);
        returnValueList = companyService.getCompanies();
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(company);
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(company));
    }

    @Test
    void testSaveCompany_ExistingRegistrationNumberDifferentId() {
        clearAndSaveDummyCompanyForId(0);
        companyService.saveCompany(company);
        companyOfSameRegistrationNumber = new Company(2, company.getRegistrationNumber(), "C", "C", List.of());

        assertThatThrownBy(() -> companyService.saveCompany(companyOfSameRegistrationNumber))
            .isInstanceOf(NonUniqueIdentifierException.class)
            .hasMessage("Specified registration number already exists: AA-11");
    }

    @Test
    void testSaveCompany_ExistingRegistrationNumberIdenticalId() {
        clearAndSaveDummyCompanyForId(0);
        companyService.saveCompany(company);
        companyOfSameRegistrationNumber = new Company(1, company.getRegistrationNumber(), "C", "C", List.of());

        returnValue = companyService.saveCompany(companyOfSameRegistrationNumber);
        returnValueList = companyService.getCompanies();
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(companyOfSameRegistrationNumber);
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, companyOfSameRegistrationNumber));
    }

    @Test
    void testUpdateCompany() {
        clearAndSaveDummyCompanyForId(2);

        returnValue = companyService.updateCompany(2, company);
        returnValueList = companyService.getCompanies();
        assertThat(returnValue.getId()).isEqualTo(2);
        assertThat(returnValue).usingRecursiveComparison().ignoringFields("id").isEqualTo(company);
        assertThat(returnValueList).usingRecursiveComparison().ignoringFields("id").isEqualTo(List.of(company));
    }

    @Test
    void testUpdateCompany_InvalidId() {
        clearAndSaveDummyCompanyForId(0);

        assertThatThrownBy(() -> companyService.updateCompany(1, company))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in memory: 1");
    }

    @Test
    void testUpdateCompany_ExistingRegistrationNumberDifferentId() {
        clearAndSaveDummyCompanyForId(0);
        companyService.saveCompany(company);
        companyOfSameRegistrationNumber = new Company(0, company.getRegistrationNumber(), "C", "C", List.of());

        assertThatThrownBy(() -> companyService.updateCompany(0, companyOfSameRegistrationNumber))
            .isInstanceOf(NonUniqueIdentifierException.class)
            .hasMessage("Specified registration number already exists: AA-11");
    }

    @Test
    void testUpdateCompany_ExistingRegistrationNumberIdenticalId() {
        clearAndSaveDummyCompanyForId(0);
        companyService.saveCompany(company);
        companyOfSameRegistrationNumber = new Company(1, company.getRegistrationNumber(), "C", "C", List.of());

        returnValue = companyService.updateCompany(1, companyOfSameRegistrationNumber);
        returnValueList = companyService.getCompanies();
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(companyOfSameRegistrationNumber);
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, companyOfSameRegistrationNumber));
    }

    @Test
    void testDeleteCompanies() {
        clearAndSaveDummyCompanyForId(0);
        companyService.saveCompany(company);
        companyService.deleteCompanies();

        returnValueList = companyService.getCompanies();
        assertThat(returnValueList).isEqualTo(List.of());
    }

    @Test
    void testDeleteCompanyById() {
        clearAndSaveDummyCompanyForId(0);
        companyService.saveCompany(company);
        companyService.deleteCompanyById(0);

        returnValueList = companyService.getCompanies();
        assertThat(returnValueList).isEqualTo(List.of(company));
    }

    @Test
    void testSaveEmployeeInCompany() {
        clearAndSaveDummyCompanyForId(0);

        returnValue = companyService.saveEmployeeInCompany(0, employee);
        returnValueList = companyService.getCompanies();
        assertThat(returnValue.getEmployees()).usingRecursiveComparison().isEqualTo(List.of(dummyEmployee, employee));
        assertThat(returnValue).usingRecursiveComparison().ignoringFields("employees").isEqualTo(dummyCompany);
        assertThat(returnValueList).usingRecursiveComparison().ignoringFields("employees").isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testSaveEmployeeInCompany_InvalidCompanyId() {
        clearAndSaveDummyCompanyForId(1);

        assertThatThrownBy(() -> companyService.saveEmployeeInCompany(0, employee))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in memory: 0");
    }

    @Test
    void testUpdateEmployeeListInCompany() {
        clearAndSaveDummyCompanyForId(0);

        returnValue = companyService.updateEmployeeListInCompany(0, List.of(dummyEmployee, employee));
        returnValueList = companyService.getCompanies();
        assertThat(returnValue.getEmployees()).usingRecursiveComparison().isEqualTo(List.of(dummyEmployee, employee));
        assertThat(returnValue).usingRecursiveComparison().ignoringFields("employees").isEqualTo(dummyCompany);
        assertThat(returnValueList).usingRecursiveComparison().ignoringFields("employees").isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateEmployeeListInCompany_InvalidCompanyId() {
        clearAndSaveDummyCompanyForId(1);

        assertThatThrownBy(() -> companyService.updateEmployeeListInCompany(0, List.of(dummyEmployee, employee)))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in memory: 0");
    }

    @Test
    void testDeleteEmployeeInCompanyById() {
        clearAndSaveDummyCompanyForId(0);
        companyService.saveEmployeeInCompany(0, employee);
        companyService.deleteEmployeeInCompanyById(0, 0);

        returnValueList = companyService.getCompanies();
        assertThat(returnValueList.get(0).getEmployees()).usingRecursiveComparison().isEqualTo(List.of(employee));
        assertThat(returnValueList).usingRecursiveComparison().ignoringFields("employees").isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testDeleteEmployeeInCompany_InvalidCompanyId() {
        clearAndSaveDummyCompanyForId(1);
        companyService.saveEmployeeInCompany(1, employee);

        assertThatThrownBy(() -> companyService.deleteEmployeeInCompanyById(0, 1))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in memory: 0");
    }

    // --- private methods ----------------------------------------------------

    private void  clearAndSaveDummyCompanyForId(long id) {
        Company dummy = new Company(
            id,
            dummyCompany.getRegistrationNumber(),
            dummyCompany.getName(),
            dummyCompany.getAddress(),
            dummyCompany.getEmployees()
        );
        companyService.deleteCompanies();
        companyService.saveCompany(dummy);
    }
}