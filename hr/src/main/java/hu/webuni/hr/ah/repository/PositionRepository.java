package hu.webuni.hr.ah.repository;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.Position;
import hu.webuni.hr.ah.model.base.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    List<Position> findByNameContainingIgnoreCase(String positionNamePattern);

    List<Position> findByRequiredQualification(Qualification requiredQualification);

    List<Position> findByMinimumSalaryGreaterThanEqual(int minimumSalary);

    @Query(
        "SELECT DISTINCT p FROM Employee e JOIN e.position p JOIN e.company c " +
        "WHERE p.id = e.position.id AND e.company.id = :companyId"
    )
    List<Position> findByCompany(long companyId);

    @Query(
        "SELECT e FROM Employee e JOIN e.position p " +
        "WHERE p.id = :positionId"
    )
    List<Employee> findEmployeesByPosition(long positionId);

    @Query(
        "SELECT e FROM Employee e JOIN e.position p " +
        "WHERE UPPER(p.name) LIKE UPPER(:positionNamePattern)"
    )
    List<Employee> findEmployeesByPositionName(String positionNamePattern);
}