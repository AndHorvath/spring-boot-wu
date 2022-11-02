package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.*;
import hu.webuni.hr.ah.service.employee.AbstractEmployeeService;
import hu.webuni.hr.ah.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    // --- employee salary methods --------------------------------------------

    public void setSalaryOfEmployee(Employee employee) {
        int salary = employee.getSalary();
        int payRaisePercent = employeeService.getPayRaisePercent(employee);

        employee.setSalary((int) (salary * (1 + payRaisePercent / 100.0)));
    }

    public EmployeeSalaryCondition getEmployeesSalaryConditionById(long id) {
        return getEmployeesSalaryCondition(((AbstractEmployeeService) employeeService).getEmployeeById(id));
    }

    public EmployeeSalaryCondition getEmployeesSalaryCondition(Employee employee) {
        EmployeeSalaryCondition employeeSalaryCondition = new EmployeeSalaryCondition(employee);
        employeeSalaryCondition.setPayRaisePercent(employeeService.getPayRaisePercent(employee));
        employeeSalaryCondition.setRaisedSalary(getRaisedSalary(employee));
        return employeeSalaryCondition;
    }

    // --- company salary methods ---------------------------------------------

    public CompanySalaryCondition getCompanySalaryConditionById(long id) {
        Company company = companyService.getCompanyById(id);
        List<Object[]> averageSalariesOfPositions = companyService.getAverageSalariesOfPositionsByCompanyId(id);
        return createSalaryCondition(company, averageSalariesOfPositions);
    }

    // --- private methods ----------------------------------------------------

    private int getRaisedSalary(Employee employee) {
        Employee auxEmployee = new Employee(employee);
        setSalaryOfEmployee(auxEmployee);
        return auxEmployee.getSalary();
    }

    private CompanySalaryCondition createSalaryCondition(Company company, List<Object[]> averageSalariesOfPositions) {
        return new CompanySalaryCondition(company, createPositionSalaryConditions(averageSalariesOfPositions));
    }

    private List<PositionSalaryCondition> createPositionSalaryConditions(List<Object[]> averageSalariesOfPositions) {
        List<PositionSalaryCondition> positionSalaryConditions = new ArrayList<>();
        try {
            averageSalariesOfPositions.forEach(averageSalaryOfPosition -> positionSalaryConditions.add(
                new PositionSalaryCondition((String) averageSalaryOfPosition[0], (double) averageSalaryOfPosition[1])
            ));
        } catch (ClassCastException exception) {
            throw new IllegalStateException("Unexpected response type");
        }
        return positionSalaryConditions;
    }
}