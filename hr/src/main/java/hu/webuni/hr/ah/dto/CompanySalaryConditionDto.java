package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.CompanyDataView;
import hu.webuni.hr.ah.view.SalaryDataView;

import java.util.List;

public class CompanySalaryConditionDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(CompanyDataView.BaseDataView.class)
    private CompanyDto company;

    @JsonProperty("salaryConditions")
    @JsonView(SalaryDataView.PositionCompleteDataView.class)
    private List<PositionSalaryConditionDto> positionSalaryConditions;

    // --- constructors -------------------------------------------------------

    public CompanySalaryConditionDto(CompanyDto company, List<PositionSalaryConditionDto> positionSalaryConditions) {
        this.company = company;
        this.positionSalaryConditions = positionSalaryConditions;
    }

    // --- getters and setters ------------------------------------------------

    public CompanyDto getCompany() { return company; }
    public List<PositionSalaryConditionDto> getPositionSalaryConditions() { return positionSalaryConditions; }
}