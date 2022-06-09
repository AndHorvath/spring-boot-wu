package hu.webuni.hr.ah.model;

public class SalaryCondition {

    // --- attributes ---------------------------------------------------------

    private Employee employee;
    private int payRaisePercent;
    private int raisedSalary;

    // --- constructors -------------------------------------------------------

    public SalaryCondition(Employee employee) {
        this.employee = employee;
    }

    // --- getters and setters ------------------------------------------------

    public Employee getEmployee() { return employee; }
    public int getPayRaisePercent() { return payRaisePercent; }
    public int getRaisedSalary() { return raisedSalary; }

    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setPayRaisePercent(int payRaisePercent) { this.payRaisePercent = payRaisePercent; }
    public void setRaisedSalary(int raisedSalary) { this.raisedSalary = raisedSalary; }
}