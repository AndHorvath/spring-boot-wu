package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.*;
import hu.webuni.hr.ah.model.base.Qualification;
import hu.webuni.hr.ah.model.sample.TestPosition;
import hu.webuni.hr.ah.repository.CompanyRepository;
import hu.webuni.hr.ah.repository.EmployeeRepository;
import hu.webuni.hr.ah.repository.PositionRepository;
import hu.webuni.hr.ah.validation.identifier.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.identifier.NonExistingIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DataObjectIdentifierValidator identifierValidator;

    // --- public methods -----------------------------------------------------

    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    public List<Position> getPositionsOrderedByProperty(String propertyName, boolean isAscending) {
        return isAscending ?
            positionRepository.findAll(Sort.by(propertyName).ascending()) :
            positionRepository.findAll(Sort.by(propertyName).descending());
    }

    public PageResult<Position> getPositionsWithPagination(Pageable pageable) {
        return new PageResult<>(pageable, positionRepository.findAll(pageable));
    }

    public Position getPositionById(long id) {
        return positionRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
    }

    public List<Position> getPositionsByRequiredQualification(String requiredQualification) {
        return positionRepository.findByRequiredQualification(Qualification.valueOf(requiredQualification));
    }

    public List<Position> getPositionsByMinimumSalary(int minimumSalary) {
        return positionRepository.findByMinimumSalaryGreaterThanEqual(minimumSalary);
    }

    public List<Position> getPositionsByCompany(long companyId) {
        validateParameter(companyId, companyRepository);
        return positionRepository.findByCompany(companyId);
    }

    public List<Employee> getEmployeesByPosition(long positionId) {
        validateParameter(positionId, positionRepository);
        return positionRepository.findEmployeesByPosition(positionId);
    }

    public List<Employee> getEmployeesByPositionName(String positionNamePart) {
        return positionRepository.findEmployeesByPositionName(createPatternForQuery(positionNamePart));
    }

    @Transactional
    public List<Position> setTestData() {
        initializeTestData();
        return getPositions();
    }

    @Transactional
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Transactional
    public Position updatePosition(long idToUpdate, Position position) {
        validateParameter(idToUpdate, positionRepository);
        return positionRepository.save(position.createCopyWithId(idToUpdate));
    }

    @Transactional
    public void deletePositions() {
        prepareEmployeesForRemovePositions();
        positionRepository.deleteAll();
    }

    @Transactional
    public void deletePositionById (long id) {
        validateParameter(id, positionRepository);
        prepareEmployeesForRemovePosition(id);
        positionRepository.deleteById(id);
    }

    // --- private methods ----------------------------------------------------

    private void validateParameter(long id, JpaRepository<?, Long> repository) {
        identifierValidator.validateIdentifierExistence(repository, id);
    }

    private void initializeTestData() {
        initializeRepository();
        TestPosition.initializeList().forEach(this::savePosition);
    }

    private void initializeRepository() {
        if (!getPositions().isEmpty()) {
            deletePositions();
        }
    }

    private void prepareEmployeesForRemovePositions() {
        getPositions().forEach(position -> prepareEmployeesForRemovePosition(position.getId()));
    }

    private void prepareEmployeesForRemovePosition(long positionId) {
        getEmployeesByPosition(positionId).forEach(this::prepareEmployeeForRemovePosition);
    }

    private void prepareEmployeeForRemovePosition(Employee employee) {
        employee.setPosition(null);
        employeeRepository.save(employee);
    }

    private String createPatternForQuery(String namePart) {
        return "%" + namePart + "%";
    }
}