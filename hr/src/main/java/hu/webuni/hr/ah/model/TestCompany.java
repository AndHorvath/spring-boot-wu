package hu.webuni.hr.ah.model;

import java.util.ArrayList;
import java.util.List;

public class TestCompany {

    public static List<Company> initializeList() {
        return new ArrayList<>(List.of(
            new Company(1L, "AA-11", "CompanyA", "AddressA", new ArrayList<>()),
            new Company(2L, "BB-22", "CompanyB", "AddressB", new ArrayList<>()),
            new Company(3L, "CC-33", "CompanyC", "AddressC", new ArrayList<>()),
            new Company(4L, "DD-44", "CompanyD", "AddressD", new ArrayList<>())
        ));
    }
}