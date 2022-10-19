package hu.webuni.hr.ah.model.sample;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestCompany {

    // --- attributes ---------------------------------------------------------

    private static String companyPrefix;
    private static Map<Position, Boolean> companyPositionRegister;
    private static List<Employee> companyEmployeeList;

    // --- public methods -----------------------------------------------------

    public static List<Company> initializeList() {
        return new ArrayList<>(List.of(
            new Company(
                "AA-11", "CompanyA", "AddressA",
                TestCompanyType.initializeList().get(0),
                createEmployeeListOfLengthWithCompanyPrefix(1, "A.")
            ),
            new Company(
                "BB-22", "CompanyB", "AddressB",
                TestCompanyType.initializeList().get(1),
                createEmployeeListOfLengthWithCompanyPrefix(3, "B.")
            ),
            new Company(
                "CC-33", "CompanyC", "AddressC",
                TestCompanyType.initializeList().get(2),
                createEmployeeListOfLengthWithCompanyPrefix(6, "C.")
            ),
            new Company(
                "DD-44", "CompanyD", "AddressD",
                TestCompanyType.initializeList().get(3),
                createEmployeeListOfLengthWithCompanyPrefix(10, "D.")
            )
        ));
    }

    // --- private methods ----------------------------------------------------

    private static List<Employee> createEmployeeListOfLengthWithCompanyPrefix(int length, String companyPrefix) {
        initializeCompanyPrefix(companyPrefix);
        initializeCompanyEmployeeListOfLength(length);
        companyEmployeeList.forEach(TestCompany::setCompanyPrefix);
        return companyEmployeeList;
    }

    private static void initializeCompanyPrefix(String companyPrefix) {
        TestCompany.companyPrefix = companyPrefix;
    }

    private static void initializeCompanyEmployeeListOfLength(int length) {
        initializeCompanyPositionRegister();
        companyEmployeeList = createTestEmployeeListOfLength(length);
        companyEmployeeList.forEach(TestCompany::setCompanyPositionForEmployee);
    }

    private static void initializeCompanyPositionRegister() {
        companyPositionRegister = new HashMap<>();
        TestPosition.initializeList().forEach(position -> companyPositionRegister.put(position, false));
    }

    private static List<Employee> createTestEmployeeListOfLength(int length) {
        return TestEmployee.initializeList().stream().limit(length).collect(Collectors.toList());
    }

    private static void setCompanyPositionForEmployee(Employee employee) {
        companyPositionRegister.keySet().stream()
            .filter(position -> position.getName().equals(employee.getPosition().getName()))
            .forEach(employee::setPosition);
    }

    private static void setCompanyPrefix(Employee employee) {
        setCompanyPrefixToEmployee(employee);
        setCompanyPrefixToEmployeesPosition(employee.getPosition());
    }

    private static void setCompanyPrefixToEmployee(Employee employee) {
        employee.setName(companyPrefix + employee.getName());
    }

    private static void setCompanyPrefixToEmployeesPosition(Position employeesPosition) {
        if (!isPositionRegisteredInCompany(employeesPosition)) {
            employeesPosition.setName(companyPrefix + employeesPosition.getName());
            registerPositionInCompany(employeesPosition);
        }
    }

    private static boolean isPositionRegisteredInCompany(Position position) {
        return companyPositionRegister.get(position);
    }

    private static void registerPositionInCompany(Position position) {
        companyPositionRegister.put(position, true);
    }
}