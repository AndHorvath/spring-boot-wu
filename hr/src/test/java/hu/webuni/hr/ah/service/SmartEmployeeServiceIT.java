package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class SmartEmployeeServiceIT {

    @Autowired
    SmartEmployeeService smartEmployeeService;

    Employee employeeA;
    Employee employeeB;
    Employee employeeC;
    Employee employeeD;

    @BeforeEach
    void setUp() {
        employeeA = new Employee(1L, "EmployeeA", LocalDateTime.of(2010, 10, 20, 0, 0), "PositionA", 1400, null);
        employeeB = new Employee(2L, "EmployeeB", LocalDateTime.of(2015, 10, 20, 0, 0), "PositionB", 1300, null);
        employeeC = new Employee(3L, "EmployeeC", LocalDateTime.of(2018, 10, 20, 0, 0), "PositionC", 1200, null);
        employeeD = new Employee(4L, "EmployeeD", LocalDateTime.of(2020, 10, 20, 0, 0), "PositionD", 1100, null);
    }

    @Test
    void testGetPayRaisePercent() {
        assertThat(smartEmployeeService.getPayRaisePercent(employeeA)).isEqualTo(10);
        assertThat(smartEmployeeService.getPayRaisePercent(employeeB)).isEqualTo(5);
        assertThat(smartEmployeeService.getPayRaisePercent(employeeC)).isEqualTo(3);
        assertThat(smartEmployeeService.getPayRaisePercent(employeeD)).isEqualTo(0);
    }
}