package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.model.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto companyToDto(Company company);

    Company dtoToCompany(CompanyDto companyDto);

    List<CompanyDto> companiesToDtos(List<Company> companies);
}