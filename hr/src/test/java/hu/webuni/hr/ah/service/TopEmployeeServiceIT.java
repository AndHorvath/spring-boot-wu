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
class TopEmployeeServiceIT {

    @Autowired
    TopEmployeeService topEmployeeService;

    Employee employeeA;
    Employee employeeB;
    Employee employeeC;
    Employee employeeD;

    @BeforeEach
    void setUp() {
        employeeA = new Employee(1, "EmployeeA", LocalDateTime.of(2010, 10, 20, 0, 0), null, 1400, null);
        employeeB = new Employee(2, "EmployeeB", LocalDateTime.of(2015, 10, 20, 0, 0), null, 1300, null);
        employeeC = new Employee(3, "EmployeeC", LocalDateTime.of(2018, 10, 20, 0, 0), null, 1200, null);
        employeeD = new Employee(4, "EmployeeD", LocalDateTime.of(2020, 10, 20, 0, 0), null, 1100, null);
    }

    @Test
    void testGetPayRaisePercent() {
        assertThat(topEmployeeService.getPayRaisePercent(employeeA)).isEqualTo(10);
        assertThat(topEmployeeService.getPayRaisePercent(employeeB)).isEqualTo(5);
        assertThat(topEmployeeService.getPayRaisePercent(employeeC)).isEqualTo(2);
        assertThat(topEmployeeService.getPayRaisePercent(employeeD)).isEqualTo(1);
    }
}