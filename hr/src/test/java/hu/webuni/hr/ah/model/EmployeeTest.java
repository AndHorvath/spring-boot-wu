package hu.webuni.hr.ah.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class EmployeeTest {

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Employee", LocalDateTime.of(2020, 10, 10, 20, 20), "Position", 1000, null);
    }

    @Test
    void testToString() {
        assertThat(employee.toString()).isEqualTo(
            "Employee [name=Employee, dateOfEntry=2020-10-10T20:20, salary=1000]"
        );
    }
}