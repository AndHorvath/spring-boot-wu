package hu.webuni.hr.ah.dto;

public class EmployeeSalaryConditionDto {

    // --- attributes ---------------------------------------------------------

    private EmployeeDto employee;
    private int payRaisePercent;
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