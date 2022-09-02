package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.PageResult;
import hu.webuni.hr.ah.model.Position;
import hu.webuni.hr.ah.model.Qualification;
import hu.webuni.hr.ah.model.TestPosition;
import hu.webuni.hr.ah.repository.PositionRepository;
import hu.webuni.hr.ah.validation.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.NonExistingIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private PositionRepository positionRepository;

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
        validateParameter(idToUpdate);
        return positionRepository.save(position.createCopyWithId(idToUpdate));
    }

    @Transactional
    public void deletePositions() {
        positionRepository.deleteAll();
    }

    @Transactional
    public void deletePositionById (long id) {
        validateParameter(id);
        positionRepository.deleteById(id);
    }

    // --- private methods ----------------------------------------------------

    private void validateParameter(long id) {
        identifierValidator.validateIdentifierExistence(positionRepository, id);
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
}