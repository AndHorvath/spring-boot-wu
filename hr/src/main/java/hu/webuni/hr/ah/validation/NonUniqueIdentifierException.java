package hu.webuni.hr.ah.validation;

import hu.webuni.hr.ah.dto.CompanyDto;

public class NonUniqueIdentifierException extends InvalidIdentifierException {

    public NonUniqueIdentifierException(CompanyDto companyDto) {
        super("Specified company registration number already exists: " + companyDto.getRegistrationNumber());
    }
}