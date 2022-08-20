package hu.webuni.hr.ah.repository;

import hu.webuni.hr.ah.model.Company;
import hu.webuni.hr.ah.model.CompanyType;
import hu.webuni.hr.ah.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {

    CompanyType findByName(String name);

    @Query(
        "SELECT ct FROM Company c JOIN c.companyType ct " +
        "WHERE c.id = :companyId"
    )
    CompanyType findByCompanyId(long companyId);

    @Query(
        "SELECT c FROM Company c JOIN c.companyType ct " +
        "WHERE ct.name = :companyTypeName"
    )
    List<Company> findCompaniesByCompanyType(String companyTypeName);

    @Query(
        "SELECT e FROM Employee e JOIN e.company c JOIN c.companyType ct " +
        "WHERE ct.name = :companyTypeName"
    )
    List<Employee> findEmployeesByCompanyType(String companyTypeName);
}