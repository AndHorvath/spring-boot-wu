package hu.webuni.hr.ah.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestEmployee {

    public static List<Employee> initializeList() {
        return new ArrayList<>(List.of(
            new Employee("EmployeeA", LocalDateTime.of(2020, 10, 20, 0, 0), "PositionA", 1100),
            new Employee("EmployeeB", LocalDateTime.of(2018, 10, 20, 0, 0), "PositionB", 1200),
            new Employee("EmployeeC", LocalDateTime.of(2015, 10, 20, 0, 0), "PositionC", 1300),
            new Employee("EmployeeD", LocalDateTime.of(2010, 10, 20, 0, 0), "PositionD", 1400)
        ));
    }
}