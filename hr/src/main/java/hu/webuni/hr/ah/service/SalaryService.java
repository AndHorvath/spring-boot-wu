package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.SalaryCondition;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ah.model.Employee;

@Service
public class SalaryService {

    // --- attributes ---------------------------------------------------------

    private EmployeeService employeeService;

    // --- constructors -------------------------------------------------------

    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // --- getters and setters ------------------------------------------------

    public EmployeeService getEmployeeService() { return employeeService; }

    // --- public methods -----------------------------------------------------

    public void setSalaryOfEmployee(Employee employee) {
        int salary = employee.getSalary();
        int payRaisePercent = employeeService.getPayRaisePercent(employee);

        employee.setSalary((int) (salary * (1 + payRaisePercent / 100.0)));
    }

    public SalaryCondition getEmployeesSalaryCondition(Employee employee) {
        SalaryCondition salaryCondition = new SalaryCondition(employee);
        salaryCondition.setPayRaisePercent(employeeService.getPayRaisePercent(employee));
        salaryCondition.setRaisedSalary(getRaisedSalary(employee));
        return salaryCondition;
    }

    // --- private methods ----------------------------------------------------

    public int getRaisedSalary(Employee employee) {
        Employee auxEmployee = new Employee(employee);
        setSalaryOfEmployee(auxEmployee);
        return auxEmployee.getSalary();
    }
}