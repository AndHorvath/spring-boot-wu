package hu.webuni.hr.ah.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestEmployee {

    public static List<Employee> initializeList() {
        return new ArrayList<>(List.of(
            new Employee("EmployeeA", LocalDateTime.of(2020, 10, 20, 0, 0), "PositionA", 1000),
            new Employee("EmployeeB", LocalDateTime.of(2019, 10, 20, 0, 0), "PositionB", 1100),
            new Employee("EmployeeC", LocalDateTime.of(2018, 10, 20, 0, 0), "PositionB", 1200),
            new Employee("EmployeeD", LocalDateTime.of(2017, 10, 20, 0, 0), "PositionC", 1300),
            new Employee("EmployeeE", LocalDateTime.of(2016, 10, 20, 0, 0), "PositionC", 1400),
            new Employee("EmployeeF", LocalDateTime.of(2015, 10, 20, 0, 0), "PositionC", 1500),
            new Employee("EmployeeG", LocalDateTime.of(2014, 10, 20, 0, 0), "PositionD", 1600),
            new Employee("EmployeeH", LocalDateTime.of(2013, 10, 20, 0, 0), "PositionD", 1700),
            new Employee("EmployeeI", LocalDateTime.of(2012, 10, 20, 0, 0), "PositionD", 1800),
            new Employee("EmployeeJ", LocalDateTime.of(2011, 10, 20, 0, 0), "PositionD", 1900)
        ));
    }
}