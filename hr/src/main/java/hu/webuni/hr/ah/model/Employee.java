package hu.webuni.hr.ah.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Employee {

    // --- attributes ---------------------------------------------------------

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private LocalDateTime dateOfEntry;
    private String position;
    private int salary;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Company company;

    // --- constructors -------------------------------------------------------

    public Employee() { }

    public Employee(String name, LocalDateTime dateOfEntry, String position, int salary) {
        this.name = name;
        this.dateOfEntry = dateOfEntry;
        this.position = position;
        this.salary = salary;
    }

    public Employee(long id, String name, LocalDateTime dateOfEntry, String position, int salary, Company company) {
        this.id = id;
        this.name = name;
        this.dateOfEntry = dateOfEntry;
        this.position = position;
        this.salary = salary;
        this.company = company;
    }

    public Employee(Employee other) {
        id = other.id;
        name = other.name;
        dateOfEntry = other.dateOfEntry;
        position = other.position;
        salary = other.salary;
        company = other.company;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getDateOfEntry() { return dateOfEntry; }
    public String getPosition() { return position; }
    public int getSalary() { return salary; }
    public Company getCompany() { return company; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDateOfEntry(LocalDateTime dateOfEntry) { this.dateOfEntry = dateOfEntry; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(int salary) { this.salary = salary; }
    public void setCompany(Company company) { this.company = company; }

    // --- public methods -----------------------------------------------------

    public Employee createCopyWithId(long id) {
        return new Employee(
            id,
            this.name,
            this.dateOfEntry,
            this.position,
            this.salary,
            this.company
        );
    }

    public void deleteEmployment() {
        dateOfEntry = null;
        position = null;
        salary = 0;
        company = null;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", dateOfEntry=" + dateOfEntry + ", salary=" + salary + "]";
    }
}