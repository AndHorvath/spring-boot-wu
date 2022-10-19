package hu.webuni.hr.ah.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CompanyTest {

    Company company;

    Employee employeeA;
    Employee employeeB;
    Employee employeeC;

    @BeforeEach
    void setUp() {
        employeeA = new Employee(1, "EmployeeA", LocalDateTime.of(2020, 10, 10, 20, 20), null, 1000, null);
        employeeB = new Employee(2, "EmployeeB", LocalDateTime.of(2020, 10, 20, 10, 20), null, 1000, null);

        company = new Company(1, "AA-11", "Company", "Address", null, List.of(employeeA, employeeB));
    }

    @Test
    void testAddEmployee() {
        employeeC = new Employee(3, "EmployeeC", LocalDateTime.of(2020, 10, 20, 20, 10), null, 1000, null);
        company.addEmployee(employeeC);

        assertThat(company.getEmployees()).containsExactlyElementsOf(List.of(employeeA, employeeB, employeeC));
    }

    @Test
    void testRemoveEmployeeById() {
        company.removeEmployeeById(1);

        assertThat(company.getEmployees()).containsExactlyElementsOf(List.of(employeeB));
    }
}