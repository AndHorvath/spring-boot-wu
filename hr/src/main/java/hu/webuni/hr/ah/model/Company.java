package hu.webuni.hr.ah.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {

    // --- attributes ---------------------------------------------------------

    @Id
    @GeneratedValue
    private final Long id;

    private final String registrationNumber;

    private String name;
    private String address;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    // --- constructors -------------------------------------------------------

    public Company(Long id, String registrationNumber, String name, String address, List<Employee> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    // --- getters and setters ------------------------------------------------

    public Long getId() { return id; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<Employee> getEmployees() { return employees; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    // --- public methods -----------------------------------------------------

    public void addEmployee(Employee employee) {
        employee.setCompany(this);
        employees.add(employee);
    }

    public void removeEmployeeById(long employeeId) {
        updateEmployeeBeforeRemove(employeeId);
        employees.removeIf(employee -> employee.getId() == employeeId);
    }

    // --- private methods ----------------------------------------------------

    private void updateEmployeeBeforeRemove(long employeeId) {
        employees.forEach(
            employee -> { if (employee.getId() == employeeId) { employee.setCompany(null); } }
        );
    }
}