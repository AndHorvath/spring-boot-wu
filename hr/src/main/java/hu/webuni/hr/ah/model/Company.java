package hu.webuni.hr.ah.model;

import hu.webuni.hr.ah.dto.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

public class Company {

    // --- attributes ---------------------------------------------------------

    private final long id;
    private final String registrationNumber;

    private String name;
    private String address;
    private List<Employee> employees;

    // --- constructors -------------------------------------------------------

    public Company(long id, String registrationNumber, String name, String address, List<Employee> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<Employee> getEmployees() { return employees; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    // public methods ---------------------------------------------------------

    public void addEmployee(Employee employee) {
        employees = new ArrayList<>(employees);
        employees.add(employee);
    }

    public void removeEmployeeById(long employeeId) {
        employees = new ArrayList<>(employees);
        employees.removeIf(employee -> employee.getId() == employeeId);
    }
}