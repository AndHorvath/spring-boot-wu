package hu.webuni.hr.ah.model.sample;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.CompanyType;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestCompany {

    // --- attributes ---------------------------------------------------------

    private static List<CompanyType> testCompanyTypeList;
    private static List<Position> testPositionList;

    private static String companyPrefix;
    private static List<Employee> companyEmployeeList;

    // --- public methods -----------------------------------------------------

    public static List<Company> initializeList() {
        initializeTestCompanyTypeList();
        initializeTestPositionList();

        return new ArrayList<>(List.of(
            new Company(
                "AA-11", "CompanyA", "AddressA",
                testCompanyTypeList.get(0),
                createEmployeeListOfLengthWithCompanyPrefix(1, "A.")
            ),
            new Company(
                "BB-22", "CompanyB", "AddressB",
                testCompanyTypeList.get(1),
                createEmployeeListOfLengthWithCompanyPrefix(3, "B.")
            ),
            new Company(
                "CC-33", "CompanyC", "AddressC",
                testCompanyTypeList.get(2),
                createEmployeeListOfLengthWithCompanyPrefix(6, "C.")
            ),
            new Company(
                "DD-44", "CompanyD", "AddressD",
                testCompanyTypeList.get(3),
                createEmployeeListOfLengthWithCompanyPrefix(10, "D.")
            )
        ));
    }

    public static List<Company> initializeBaseList() {
        List<Company> companyBaseList = initializeList();
        companyBaseList.forEach(Company::clearEmployeeList);
        return companyBaseList;
    }

    // --- private methods ----------------------------------------------------

    private static List<Employee> createEmployeeListOfLengthWithCompanyPrefix(int length, String companyPrefix) {
        initializeCompanyPrefix(companyPrefix);
        initializeCompanyEmployeeListOfLength(length);

        companyEmployeeList.forEach(TestCompany::setPositionForEmployee);
        companyEmployeeList.forEach(TestCompany::setCompanyPrefixToEmployee);
        return companyEmployeeList;
    }

    private static void initializeTestCompanyTypeList() {
        testCompanyTypeList = TestCompanyType.initializeList();
    }

    private static void initializeTestPositionList() {
        testPositionList = TestPosition.initializeList();
    }

    private static void initializeCompanyPrefix(String companyPrefix) {
        TestCompany.companyPrefix = companyPrefix;
    }

    private static void initializeCompanyEmployeeListOfLength(int length) {
        companyEmployeeList = TestEmployee.initializeList().stream()
            .limit(length).collect(Collectors.toList());
    }

    private static void setPositionForEmployee(Employee employee) {
        testPositionList.stream()
            .filter(position -> position.isOfSameName(employee.getPosition()))
            .findFirst()
            .ifPresentOrElse(employee::setPosition, employee::deletePosition);
    }

    private static void setCompanyPrefixToEmployee(Employee employee) {
        employee.setName(companyPrefix + employee.getName());
    }
}