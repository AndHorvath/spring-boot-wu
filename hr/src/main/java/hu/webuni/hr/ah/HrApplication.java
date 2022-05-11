package hu.webuni.hr.ah;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	// --- getters and setters ------------------------------------------------
	
	public SalaryService getSalaryService() { return salaryService;	}
	
	// --- public methods -----------------------------------------------------

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createTestEmployees().forEach(employee -> createEmployeeOutput(employee));
	}
	
	// --- private methods ----------------------------------------------------
	
	private List<Employee> createTestEmployees() {
		List<Employee> testEmployees = new ArrayList<>();
		testEmployees.addAll(List.of(
			new Employee(1L, "EmployeeA", LocalDateTime.of(2010, 10, 20, 0, 0), "PositionA", 1000),
			new Employee(2L, "EmployeeB", LocalDateTime.of(2015, 10, 20, 0, 0), "PositionB", 1000),
			new Employee(3L, "EmployeeC", LocalDateTime.of(2018, 10, 20, 0, 0), "PositionC", 1000),
			new Employee(4L, "EmployeeD", LocalDateTime.of(2020, 10, 20, 0, 0), "PositionD", 1000)
		));
		return testEmployees;
	}
	
	private void createEmployeeOutput(Employee employee) {
		System.out.print(employee.toString());
		salaryService.setSalaryOfEmployee(employee);
		System.out.println(", raised salary=" + employee.getSalary());
	}
}