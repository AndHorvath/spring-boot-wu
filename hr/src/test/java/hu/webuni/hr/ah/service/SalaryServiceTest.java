package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.SalaryCondition;
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

    SalaryService salaryServiceForArbitraryEmployeeInstances;

    @Mock
    EmployeeService employeeService;

    Employee employee;

    SalaryCondition salaryCondition;

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000, null);
    }

    @Test
    void testSetSalaryOfEmployee() {
        when(employeeService.getPayRaisePercent(employee)).thenReturn(15);
        salaryService.setSalaryOfEmployee(employee);

        assertThat(employee.getSalary()).isEqualTo(1150);
    }

    @Test
    void testGetEmployeesSalaryCondition() {
        salaryServiceForArbitraryEmployeeInstances = new SalaryService(arbitraryEmployee -> 15);
        salaryCondition = salaryServiceForArbitraryEmployeeInstances.getEmployeesSalaryCondition(employee);

        assertThat(salaryCondition.getEmployee()).usingRecursiveComparison().isEqualTo(employee);
        assertThat(salaryCondition.getPayRaisePercent()).isEqualTo(15);
        assertThat(salaryCondition.getRaisedSalary()).isEqualTo(1150);
    }
}