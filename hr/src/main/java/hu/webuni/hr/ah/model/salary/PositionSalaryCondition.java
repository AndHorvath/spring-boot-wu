package hu.webuni.hr.ah.model.salary;

public class PositionSalaryCondition {

    // --- attributes ---------------------------------------------------------

    private String designation;
    private double averageSalary;

    // --- constructors -------------------------------------------------------

    public PositionSalaryCondition(String designation, double averageSalary) {
        this.designation = designation;
        this.averageSalary = averageSalary;
    }

    // --- getters and setters ------------------------------------------------

    public String getDesignation() { return designation; }
    public double getAverageSalary() { return averageSalary; }
}