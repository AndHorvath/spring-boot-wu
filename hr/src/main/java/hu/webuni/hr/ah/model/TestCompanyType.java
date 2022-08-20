package hu.webuni.hr.ah.model;

import java.util.ArrayList;
import java.util.List;

public class TestCompanyType {

    public static List<CompanyType> initializeList() {
        List<CompanyType> testList = new ArrayList<>();
        CompanyTypeBase.getAllValues().forEach(baseTypeName -> updateTestList(testList, baseTypeName));
        return testList;
    }

    // --- private methods ----------------------------------------------------

    private static void updateTestList(List<CompanyType> testList, String typeName) {
        testList.add(new CompanyType(typeName));
    }
}