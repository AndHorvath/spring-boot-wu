package hu.webuni.hr.ah.dto;

public enum CompanyTypeDto {

    LIMITED_PARTNERSHIP("Limited Partnership"),
    PRIVATE_LIMITED_COMPANY("Private Limited Partnership"),
    PRIVATE_COMPANY_LIMITED_BY_SHARES("Private Company Limited by Shares"),
    PUBLIC_LIMITED_COMPANY("Public Limited Company");

    // --- attributes ---------------------------------------------------------

    private final String value;

    // --- constructors -------------------------------------------------------

    CompanyTypeDto(String value) {
        this.value = value;
    }

    // --- getters and setters ------------------------------------------------

    public String getValue() { return value; }
}