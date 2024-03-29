package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class EmployeeServiceTest {

    EmployeeService employeeServiceImplementation;

    Employee employeeA;
    Employee employeeB;

    @BeforeEach
    void setUp() {
        employeeServiceImplementation = employee -> 0;

        employeeA = new Employee(1, "EmployeeA", LocalDateTime.now().minusMonths(18).minusDays(1), null, 100, null);
        employeeB = new Employee(2, "EmployeeB", LocalDateTime.now().minusMonths(18).plusDays(1), null, 1000, null);
    }

    @Test
    void testIsEmploymentLongerThanGivenYears() {
        assertThat(employeeServiceImplementation.isEmploymentLongerThanGivenYears(employeeA, 1.5)).isTrue();
        assertThat(employeeServiceImplementation.isEmploymentLongerThanGivenYears(employeeB, 1.5)).isFalse();
    }
}