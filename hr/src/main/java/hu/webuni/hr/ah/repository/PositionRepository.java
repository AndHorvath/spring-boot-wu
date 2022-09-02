package hu.webuni.hr.ah.repository;

import hu.webuni.hr.ah.model.Position;
import hu.webuni.hr.ah.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    List<Position> findByRequiredQualification(Qualification requiredQualification);

    List<Position> findByMinimumSalaryGreaterThanEqual(int minimumSalary);
}