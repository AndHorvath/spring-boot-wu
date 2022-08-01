package hu.webuni.hr.ah.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {

    // --- attributes ---------------------------------------------------------

    @Id
    @GeneratedValue
    private long id;

    private String registrationNumber;

    private String name;
    private String address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Employee> employees;

    // --- constructors -------------------------------------------------------

    public Company() { }

    public Company(String registrationNumber, String name, String address, List<Employee> employees) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

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

    public void setId(long id) { this.id = id; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    // --- public methods -----------------------------------------------------

    public void addEmployee(Employee employee) {
        prepareEmployeeForAddition(employee);
        employees.add(employee);
    }

    public void addEmployees(List<Employee> employees) {
        employees.forEach(this::addEmployee);
    }

    public void removeEmployeeById(long employeeId) {
        prepareEmployeeForRemove(employeeId);
        employees.removeIf(employee -> employee.getId() == employeeId);
    }

    public void clearEmployeeList() {
        employees.forEach(this::prepareEmployeeForRemove);
        employees.clear();
    }

    public Company createCopyWithId(long id) {
        return new Company(
            id,
            this.registrationNumber,
            this.name,
            this.address,
            this.employees
        );
    }

    // --- private methods ----------------------------------------------------

    private void prepareEmployeeForAddition(Employee employee) {
        employee.setCompany(this);
    }

    private void prepareEmployeeForRemove(Employee employee) {
        prepareEmployeeForRemove(employee.getId());
    }

    private void prepareEmployeeForRemove(long employeeId) {
        employees.stream()
            .filter(employee -> employee.getId() == employeeId)
            .forEach(employee -> employee.setCompany(null));
    }
}