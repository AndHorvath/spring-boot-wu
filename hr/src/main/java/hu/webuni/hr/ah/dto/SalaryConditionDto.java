package hu.webuni.hr.ah.dto;

public class SalaryConditionDto {

    // --- attributes ---------------------------------------------------------

    private EmployeeDto employee;
    private int payRaisePercent;
    private int raisedSalary;

    // --- constructors -------------------------------------------------------

    public SalaryConditionDto(EmployeeDto employee) {
        this.employee = employee;
    }

    // --- getters and setters ------------------------------------------------

    public EmployeeDto getEmployee() { return employee; }
    public int getPayRaisePercent() { return payRaisePercent; }
    public int getRaisedSalary() { return raisedSalary; }

    public void setEmployee(EmployeeDto employee) { this.employee = employee; }
    public void setPayRaisePercent(int payRaisePercent) { this.payRaisePercent = payRaisePercent; }
    public void setRaisedSalary(int raisedSalary) { this.raisedSalary = raisedSalary; }
}