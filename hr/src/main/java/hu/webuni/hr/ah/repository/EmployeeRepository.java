package hu.webuni.hr.ah.repository;

import hu.webuni.hr.ah.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findBySalaryGreaterThan(int salaryLimit);

    @Query("SELECT e FROM Employee e JOIN e.position p WHERE p.name = :positionName")
    List<Employee> findByPosition(String positionName);

    List<Employee> findByNameStartingWithIgnoreCase(String nameStart);

    List<Employee> findByDateOfEntryBetween(LocalDateTime lowerDateLimit, LocalDateTime upperDateLimit);
}