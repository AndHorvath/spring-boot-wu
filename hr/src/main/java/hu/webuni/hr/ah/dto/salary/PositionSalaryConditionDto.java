package hu.webuni.hr.ah.dto.salary;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.SalaryDataView;

public class PositionSalaryConditionDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(SalaryDataView.PositionBaseDataView.class)
    private String designation;

    @JsonView(SalaryDataView.PositionCompleteDataView.class)
    private double averageSalary;

    // --- constructors -------------------------------------------------------

    public PositionSalaryConditionDto(String designation, double averageSalary) {
        this.designation = designation;
        this.averageSalary = averageSalary;
    }

    // --- getters and setters ------------------------------------------------

    public String getDesignation() { return designation; }
    public double getAverageSalary() { return averageSalary; }
}