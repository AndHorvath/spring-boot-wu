package hu.webuni.hr.ah.service;

import java.time.LocalDateTime;

import hu.webuni.hr.ah.model.Employee;

public interface EmployeeService {

    int getPayRaisePercent(Employee employee);

    default boolean isEmploymentLongerThanGivenYears(Employee employee, double years) {
        LocalDateTime dateOfEntry = employee.getDateOfEntry();
        LocalDateTime now = LocalDateTime.now();
        int months = (int) (years * 12);

        return now.minusMonths(months).isAfter(dateOfEntry);
    }
}