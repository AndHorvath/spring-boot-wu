package hu.webuni.hr.ah.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestCompany {

    public static List<Company> initializeList() {
        return new ArrayList<>(List.of(
            new Company(
                "AA-11", "CompanyA", "AddressA",
                TestCompanyType.initializeList().get(0),
                createTestEmployeeListOfLengthWithNamePrefix(1, "A.")
            ),
            new Company(
                "BB-22", "CompanyB", "AddressB",
                TestCompanyType.initializeList().get(1),
                createTestEmployeeListOfLengthWithNamePrefix(3, "B.")
            ),
            new Company(
                "CC-33", "CompanyC", "AddressC",
                TestCompanyType.initializeList().get(2),
                createTestEmployeeListOfLengthWithNamePrefix(6, "C.")
            ),
            new Company(
                "DD-44", "CompanyD", "AddressD",
                TestCompanyType.initializeList().get(3),
                createTestEmployeeListOfLengthWithNamePrefix(10, "D.")
            )
        ));
    }

    // --- private methods ----------------------------------------------------

    private static List<Employee> createTestEmployeeListOfLengthWithNamePrefix(int length, String namePrefix) {
        List<Employee> testEmployeeList = createTestEmployeeListOfLength(length);
        testEmployeeList.forEach(employee -> employee.setName(namePrefix + employee.getName()));
        return testEmployeeList;
    }

    private static List<Employee> createTestEmployeeListOfLength(int length) {
        return TestEmployee.initializeList().stream().limit(length).collect(Collectors.toList());
    }
}