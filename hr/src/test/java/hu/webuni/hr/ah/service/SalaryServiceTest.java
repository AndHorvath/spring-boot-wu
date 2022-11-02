package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryServiceTest {

    @InjectMocks
    SalaryService salaryService;

    @Mock
    EmployeeService employeeService;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), null, 1000, null);
    }

    @Test
    void testSetSalaryOfEmployee() {
        when(employeeService.getPayRaisePercent(employee)).thenReturn(15);
        salaryService.setSalaryOfEmployee(employee);

        assertThat(employee.getSalary()).isEqualTo(1150);
    }
}