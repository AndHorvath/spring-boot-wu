package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.EmployeeDataView;
import hu.webuni.hr.ah.view.SalaryDataView;

public class EmployeeSalaryConditionDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(EmployeeDataView.BaseDataView.class)
    private EmployeeDto employee;

    @JsonView(SalaryDataView.EmployeeBaseDataView.class)
    private int payRaisePercent;

    @JsonView(SalaryDataView.EmployeeBaseDataView.class)
    private int raisedSalary;

    // --- constructors -------------------------------------------------------

    public EmployeeSalaryConditionDto(EmployeeDto employee, int payRaisePercent, int raisedSalary) {
        this.employee = employee;
        this.payRaisePercent = payRaisePercent;
        this.raisedSalary = raisedSalary;
    }

    // --- getters and setters ------------------------------------------------

    public EmployeeDto getEmployee() { return employee; }
    public int getPayRaisePercent() { return payRaisePercent; }
    public int getRaisedSalary() { return raisedSalary; }
}