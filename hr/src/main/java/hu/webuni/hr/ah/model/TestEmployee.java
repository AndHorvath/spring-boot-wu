package hu.webuni.hr.ah.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestEmployee {

    public static List<Employee> initializeList() {
        return new ArrayList<>(List.of(
            new Employee(1, "EmployeeA", LocalDateTime.of(2010, 10, 20, 0, 0), "PositionA", 1400),
            new Employee(2, "EmployeeB", LocalDateTime.of(2015, 10, 20, 0, 0), "PositionB", 1300),
            new Employee(3, "EmployeeC", LocalDateTime.of(2018, 10, 20, 0, 0), "PositionC", 1200),
            new Employee(4, "EmployeeD", LocalDateTime.of(2020, 10, 20, 0, 0), "PositionD", 1100)
        ));
    }
}