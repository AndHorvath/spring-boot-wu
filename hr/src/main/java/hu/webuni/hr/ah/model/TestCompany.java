package hu.webuni.hr.ah.model;

import hu.webuni.hr.ah.dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;

public class TestCompany {

    public static List<Company> initializeList() {
        return new ArrayList<>(List.of(
            new Company(1, "AA-1", "CompanyA", "AddressA", TestEmployee.initializeDtoList().stream().limit(1).toList()),
            new Company(2, "BB-2", "CompanyB", "AddressB", TestEmployee.initializeDtoList().stream().limit(2).toList()),
            new Company(3, "CC-3", "CompanyC", "AddressC", TestEmployee.initializeDtoList().stream().limit(3).toList()),
            new Company(4, "DD-4", "CompanyD", "AddressD", TestEmployee.initializeDtoList().stream().limit(4).toList())
        ));
    }

    public static List<CompanyDto> initializeDtoList() {
        return initializeList().stream().map(Company::toDto).toList();
    }
}