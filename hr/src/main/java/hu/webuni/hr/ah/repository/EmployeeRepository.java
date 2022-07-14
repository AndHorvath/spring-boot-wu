package hu.webuni.hr.ah.repository;

import hu.webuni.hr.ah.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findBySalaryGreaterThan(int salaryLimit);
}