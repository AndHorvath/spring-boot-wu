package hu.webuni.hr.ah.validation;

import hu.webuni.hr.ah.model.Company;

public class NonUniqueIdentifierException extends InvalidIdentifierException {

    public NonUniqueIdentifierException(Company company) {
        super("Specified registration number already exists: " + company.getRegistrationNumber());
    }
}