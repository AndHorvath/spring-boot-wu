package hu.webuni.hr.ah.model;

import hu.webuni.hr.ah.dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;

public class TestCompany {

    public static List<Company> initializeList() {
        return new ArrayList<>(List.of(
            new Company("AA-11", "CompanyA", "Address-A", TestEmployee.initializeDtoList().stream().limit(1).toList()),
            new Company("BB-22", "CompanyB", "Address-B", TestEmployee.initializeDtoList().stream().limit(2).toList()),
            new Company("CC-33", "CompanyC", "Address-C", TestEmployee.initializeDtoList().stream().limit(3).toList()),
            new Company("DD-44", "CompanyD", "Address-D", TestEmployee.initializeDtoList().stream().limit(4).toList())
        ));
    }

    public static List<CompanyDto> initializeDtoList() {
        return initializeList().stream().map(Company::toDto).toList();
    }
}