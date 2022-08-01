package hu.webuni.hr.ah.repository;

import hu.webuni.hr.ah.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> { }