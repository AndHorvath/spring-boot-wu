package hu.webuni.hr.ah.model;

import java.util.ArrayList;
import java.util.List;

public class TestCompany {

    public static List<Company> initializeList() {
        return new ArrayList<>(List.of(
            new Company(1, "AA-11", "CompanyA", "AddressA", new ArrayList<>()),
            new Company(2, "BB-22", "CompanyB", "AddressB", new ArrayList<>()),
            new Company(3, "CC-33", "CompanyC", "AddressC", new ArrayList<>()),
            new Company(4, "DD-44", "CompanyD", "AddressD", new ArrayList<>())
        ));
    }
}