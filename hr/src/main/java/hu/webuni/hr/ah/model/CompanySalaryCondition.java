package hu.webuni.hr.ah.model;

import java.util.List;

public class CompanySalaryCondition {

    // --- attributes ---------------------------------------------------------

    private Company company;
    private List<PositionSalaryCondition> positionSalaryConditions;

    // --- constructors -------------------------------------------------------

    public CompanySalaryCondition(Company company, List<PositionSalaryCondition> positionSalaryConditions) {
        this.company = company;
        this.positionSalaryConditions = positionSalaryConditions;
    }

    // --- getters and setters ------------------------------------------------

    public Company getCompany() { return company; }
    public List<PositionSalaryCondition> getPositionSalaryConditions() { return positionSalaryConditions; }
}