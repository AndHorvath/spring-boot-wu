package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.PositionDataView;

public class PositionSalaryConditionDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(PositionDataView.BaseDataView.class)
    private String designation;

    @JsonView(PositionDataView.CompleteDataView.class)
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