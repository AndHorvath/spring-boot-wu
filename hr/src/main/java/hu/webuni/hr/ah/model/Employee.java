package hu.webuni.hr.ah.model;

import java.time.LocalDateTime;

public class Employee {

    // --- attributes ---------------------------------------------------------

    private long id;
    private String name;
    private LocalDateTime dateOfEntry;
    private String position;
    private int salary;

    // --- constructors -------------------------------------------------------

    public Employee() { }

    public Employee(long id, String name, LocalDateTime dateOfEntry, String position, int salary) {
        this.id = id;
        this.name = name;
        this.dateOfEntry = dateOfEntry;
        this.position = position;
        this.salary = salary;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getDateOfEntry() { return dateOfEntry; }
    public String getPosition() { return position; }
    public int getSalary() { return salary; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDateOfEntry(LocalDateTime dateOfEntry) { this.dateOfEntry = dateOfEntry; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(int salary) { this.salary = salary; }

    // --- public methods -----------------------------------------------------

    @Override
    public String toString() {
        return "Employee [name=" + name + ", dateOfEntry=" + dateOfEntry + ", salary=" + salary + "]";
    }
}