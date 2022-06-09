package hu.webuni.hr.ah.model;

import java.util.ArrayList;
import java.util.List;

public class TestCompany {

    public static List<Company> initializeList() {
        return new ArrayList<>(List.of(
            new Company(1, "AA-11", "CompanyA", "AddressA", TestEmployee.initializeList().stream().limit(1).toList()),
            new Company(2, "BB-22", "CompanyB", "AddressB", TestEmployee.initializeList().stream().limit(2).toList()),
            new Company(3, "CC-33", "CompanyC", "AddressC", TestEmployee.initializeList().stream().limit(3).toList()),
            new Company(4, "DD-44", "CompanyD", "AddressD", TestEmployee.initializeList().stream().limit(4).toList())
        ));
    }
}