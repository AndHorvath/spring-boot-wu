package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.CompanyTypeDto;
import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.CompanyType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    // --- company mappings ---------------------------------------------------

    CompanyDto companyToDto(Company company);

    Company dtoToCompany(CompanyDto companyDto);

    List<CompanyDto> companiesToDtos(List<Company> companies);

    // --- company type mappings ----------------------------------------------

    CompanyTypeDto companyTypeToDto(CompanyType companyType);

    CompanyType dtoToCompanyType(CompanyTypeDto companyTypeDto);
}