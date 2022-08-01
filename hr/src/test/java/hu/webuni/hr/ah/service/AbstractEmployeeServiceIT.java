package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.validation.NonExistingIdentifierException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AbstractEmployeeServiceIT {

    @Autowired
    AbstractEmployeeService employeeService;

    Employee employee;

    Employee dummy;

    Employee returnValue;
    List<Employee> returnValueList;

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000, null);

        dummy = new Employee(0, "D", LocalDateTime.of(1, 1, 1, 1, 1), "D", 1, null);
    }

    @Test
    void testGetEmployees() {
        clearAndSaveDummyEmployeeForId(0);

        returnValueList = employeeService.getEmployees();
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testGetEmployeeById() {
        clearAndSaveDummyEmployeeForId(0);

        returnValue = employeeService.getEmployeeById(0);
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(dummy);
    }

    @Test
    void testGetEmployeesById_InvalidId() {
        clearAndSaveDummyEmployeeForId(1);

        assertThatThrownBy(() -> employeeService.getEmployeeById(0))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in memory: 0");
    }

    @Test
    void testGetEmployeesOverSalaryLimit_ConditionNotSatisfied() {
        clearAndSaveDummyEmployeeForId(0);

        returnValueList = employeeService.getEmployeesOverSalaryLimit(1);
        assertThat(returnValueList ).usingRecursiveComparison().isEqualTo(List.of());
    }

    @Test
    void testGetEmployeesOverSalaryLimit_ConditionSatisfied() {
        dummy.setSalary(2);
        clearAndSaveDummyEmployeeForId(0);

        returnValueList = employeeService.getEmployeesOverSalaryLimit(1);
        assertThat(returnValueList ).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testSaveEmployee() {
        clearAndSaveDummyEmployeeForId(0);

        returnValue = employeeService.saveEmployee(employee);
        returnValueList = employeeService.getEmployees();
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(employee);
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(dummy, employee));
    }

    @Test
    void testSaveEmployee_ExistingId() {
        clearAndSaveDummyEmployeeForId(1);

        returnValue = employeeService.saveEmployee(employee);
        returnValueList = employeeService.getEmployees();
        assertThat(returnValue).usingRecursiveComparison().isEqualTo(employee);
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(employee));
    }

    @Test
    void testUpdateEmployee() {
        clearAndSaveDummyEmployeeForId(2);

        returnValue = employeeService.updateEmployee(2, employee);
        returnValueList = employeeService.getEmployees();
        assertThat(returnValue.getId()).isEqualTo(2);
        assertThat(returnValue).usingRecursiveComparison().ignoringFields("id").isEqualTo(employee);
        assertThat(returnValueList).usingRecursiveComparison().ignoringFields("id").isEqualTo(List.of(employee));
    }

    @Test
    void testUpdateEmployee_InvalidId() {
        clearAndSaveDummyEmployeeForId(0);

        assertThatThrownBy(() -> employeeService.updateEmployee(1, employee))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in memory: 1");
    }

    @Test
    void testDeleteEmployees() {
        clearAndSaveDummyEmployeeForId(0);
        employeeService.saveEmployee(employee);
        employeeService.deleteEmployees();

        returnValueList = employeeService.getEmployees();
        assertThat(returnValueList).isEqualTo(List.of());
    }

    @Test
    void testDeleteEmployeeById() {
        clearAndSaveDummyEmployeeForId(0);
        employeeService.saveEmployee(employee);
        employeeService.deleteEmployeeById(0);

        returnValueList = employeeService.getEmployees();
        assertThat(returnValueList).usingRecursiveComparison().isEqualTo(List.of(employee));
    }

    // --- private methods ----------------------------------------------------

    private void  clearAndSaveDummyEmployeeForId(long id) {
        Employee dummyEmployee = new Employee(
            id, dummy.getName(), dummy.getDateOfEntry(), dummy.getPosition(), dummy.getSalary(), null
        );
        employeeService.deleteEmployees();
        employeeService.saveEmployee(dummyEmployee);
    }
}