package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.model.DataView;

import javax.xml.crypto.Data;

public class PositionSalaryConditionDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(DataView.BaseDataView.class)
    private String designation;

    @JsonView(DataView.BaseDataView.class)
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