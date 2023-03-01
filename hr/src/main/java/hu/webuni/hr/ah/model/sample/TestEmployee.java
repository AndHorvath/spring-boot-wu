package hu.webuni.hr.ah.model.sample;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.Position;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestEmployee {

    public static List<Employee> initializeList() {
        List<Position> testPositionList = TestPosition.initializeList();

        return new ArrayList<>(List.of(
            new Employee("EmployeeA", LocalDateTime.of(2020, 10, 20, 0, 0), testPositionList.get(0), 1000),
            new Employee("EmployeeB", LocalDateTime.of(2019, 10, 20, 0, 0), testPositionList.get(0), 1100),
            new Employee("EmployeeC", LocalDateTime.of(2018, 10, 20, 0, 0), testPositionList.get(1), 1200),
            new Employee("EmployeeD", LocalDateTime.of(2017, 10, 20, 0, 0), testPositionList.get(1), 1300),
            new Employee("EmployeeE", LocalDateTime.of(2016, 10, 20, 0, 0), testPositionList.get(2), 1400),
            new Employee("EmployeeF", LocalDateTime.of(2015, 10, 20, 0, 0), testPositionList.get(2), 1500),
            new Employee("EmployeeG", LocalDateTime.of(2014, 10, 20, 0, 0), testPositionList.get(3), 1600),
            new Employee("EmployeeH", LocalDateTime.of(2013, 10, 20, 0, 0), testPositionList.get(3), 1700),
            new Employee("EmployeeI", LocalDateTime.of(2012, 10, 20, 0, 0), testPositionList.get(4), 1800),
            new Employee("EmployeeJ", LocalDateTime.of(2011, 10, 20, 0, 0), testPositionList.get(4), 1900)
        ));
    }

    public static List<Employee> initializeListWithIds() {
        List<Employee> employeeListWithIds = initializeList();
        employeeListWithIds.forEach(employee -> initializeId(employeeListWithIds, employee));
        return employeeListWithIds;
    }

    public static List<Employee> initializeBaseList() {
        List<Employee> employeeBaseList = initializeList();
        employeeBaseList.forEach(Employee::deleteEmployment);
        return employeeBaseList;
    }

    // --- private methods ----------------------------------------------------

    private static void initializeId(List<Employee> testList, Employee employee) {
        employee.setId(testList.indexOf(employee));
    }
}