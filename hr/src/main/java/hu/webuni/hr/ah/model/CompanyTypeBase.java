package hu.webuni.hr.ah.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CompanyTypeBase {

    LIMITED_PARTNERSHIP("Limited Partnership"),
    PRIVATE_LIMITED_COMPANY("Private Limited Company"),
    PRIVATE_COMPANY_LIMITED_BY_SHARES("Private Company Limited by Shares"),
    PUBLIC_LIMITED_COMPANY("Public Limited Company");

    // --- attributes ---------------------------------------------------------

    private final String value;

    // --- constructors -------------------------------------------------------

    CompanyTypeBase(String value) {
        this.value = value;
    }

    // --- getters and setters ------------------------------------------------

    public String getValue() { return value; }

    // --- public methods -----------------------------------------------------

    public static List<String> getAllValues() {
        return Arrays.stream(values()).map(CompanyTypeBase::getValue).collect(Collectors.toList());
    }
}