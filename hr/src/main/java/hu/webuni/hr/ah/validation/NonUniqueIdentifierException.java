package hu.webuni.hr.ah.validation;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.CompanyType;

public class NonUniqueIdentifierException extends InvalidIdentifierException {

    public NonUniqueIdentifierException(Company company) {
        super("Specified registration number already exists: " + company.getRegistrationNumber());
    }

    public NonUniqueIdentifierException(CompanyType companyType) {
        super("Company type name has to be unique. Specified name already exists: " + companyType.getName());
    }
}