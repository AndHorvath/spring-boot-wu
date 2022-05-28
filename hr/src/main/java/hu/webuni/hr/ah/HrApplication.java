package hu.webuni.hr.ah;

import hu.webuni.hr.ah.model.TestEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private SalaryService salaryService;

    // --- public methods -----------------------------------------------------

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        TestEmployee.initializeList().forEach(this::createEmployeeOutput);
    }

    // --- private methods ----------------------------------------------------

    private void createEmployeeOutput(Employee employee) {
        System.out.print(employee.toString());
        salaryService.setSalaryOfEmployee(employee);
        System.out.println(", raised salary=" + employee.getSalary());
    }
}