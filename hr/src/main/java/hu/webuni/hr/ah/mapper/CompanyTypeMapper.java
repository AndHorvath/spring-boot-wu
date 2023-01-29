package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.CompanyTypeDto;
import hu.webuni.hr.ah.dto.base.PageResultDto;
import hu.webuni.hr.ah.model.CompanyType;
import hu.webuni.hr.ah.model.base.PageResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyTypeMapper {

    CompanyTypeDto companyTypeToDto(CompanyType companyType);
    CompanyType dtoToCompanyType(CompanyTypeDto companyTypeDto);

    List<CompanyTypeDto> companyTypesToDtos(List<CompanyType> companyTypes);

    PageResultDto<CompanyTypeDto> pageResultToDto(PageResult<CompanyType> pageResult);
}