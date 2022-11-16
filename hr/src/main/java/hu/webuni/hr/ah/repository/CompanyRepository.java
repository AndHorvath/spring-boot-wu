package hu.webuni.hr.ah.repository;

import hu.webuni.hr.ah.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findDistinctByEmployeesSalaryGreaterThan(int salaryLimit);

    @Query(
        "SELECT c FROM Company c JOIN c.employees e " +
        "GROUP BY c.id HAVING COUNT(e) > :employeeLimit"
    )
    List<Company> findDistinctByNumberOfEmployeesGreaterThan(long employeeLimit);

    @Query(
        "SELECT DISTINCT c FROM Company c JOIN c.employees e JOIN e.position p " +
        "WHERE c.id = e.company.id AND e.position.id = p.id AND UPPER(p.name) LIKE UPPER(:positionNamePattern)"
    )
    List<Company> findByPosition(String positionNamePattern);

    @Query(
        "SELECT e.position.name, AVG(e.salary) FROM Company c JOIN c.employees e " +
        "GROUP BY c.id, e.position.name HAVING c.id = :companyId ORDER BY AVG(e.salary) DESC"
    )
    List<Object[]> findAverageSalariesOfPositionsByCompanyId(long companyId);
}