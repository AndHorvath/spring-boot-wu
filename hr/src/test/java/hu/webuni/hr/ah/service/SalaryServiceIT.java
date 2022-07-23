package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.SalaryCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"top", "test"})
class SalaryServiceIT {

    @Autowired
    SalaryService salaryService;

    Employee employeeA;
    Employee employeeB;
    Employee employeeC;
    Employee employeeD;

    SalaryCondition salaryCondition;

    @BeforeEach
    void setUp() {
        employeeA = new Employee(1L, "EmployeeA", LocalDateTime.of(2010, 10, 20, 0, 0), "PositionA", 1400, null);
        employeeB = new Employee(2L, "EmployeeB", LocalDateTime.of(2015, 10, 20, 0, 0), "PositionB", 1300, null);
        employeeC = new Employee(3L, "EmployeeC", LocalDateTime.of(2018, 10, 20, 0, 0), "PositionC", 1200, null);
        employeeD = new Employee(4L, "EmployeeD", LocalDateTime.of(2020, 10, 20, 0, 0), "PositionD", 1100, null);
    }

    @Test
    void testSetSalaryOfEmployee() {
        salaryService.setSalaryOfEmployee(employeeA);
        salaryService.setSalaryOfEmployee(employeeB);
        salaryService.setSalaryOfEmployee(employeeC);
        salaryService.setSalaryOfEmployee(employeeD);

        assertThat(employeeA.getSalary()).isEqualTo(1540);
        assertThat(employeeB.getSalary()).isEqualTo(1365);
        assertThat(employeeC.getSalary()).isEqualTo(1224);
        assertThat(employeeD.getSalary()).isEqualTo(1111);
    }

    @Test
    void testGetEmployeesSalaryCondition() {
        salaryCondition = salaryService.getEmployeesSalaryCondition(employeeA);

        assertThat(salaryCondition.getEmployee()).usingRecursiveComparison().isEqualTo(employeeA);
        assertThat(salaryCondition.getPayRaisePercent()).isEqualTo(10);
        assertThat(salaryCondition.getRaisedSalary()).isEqualTo(1540);
    }
}