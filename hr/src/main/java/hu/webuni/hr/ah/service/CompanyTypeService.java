package hu.webuni.hr.ah.service;

import hu.webuni.hr.ah.model.*;
import hu.webuni.hr.ah.model.base.PageResult;
import hu.webuni.hr.ah.model.sample.TestCompanyType;
import hu.webuni.hr.ah.repository.CompanyRepository;
import hu.webuni.hr.ah.repository.CompanyTypeRepository;
import hu.webuni.hr.ah.validation.identifier.DataObjectIdentifierValidator;
import hu.webuni.hr.ah.validation.identifier.NonExistingIdentifierException;
import hu.webuni.hr.ah.validation.identifier.NonUniqueIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyTypeService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DataObjectIdentifierValidator identifierValidator;

    // --- public methods -----------------------------------------------------

    public List<CompanyType> getCompanyTypes() {
        return companyTypeRepository.findAll();
    }

    public List<CompanyType> getCompanyTypesOrderedByProperty(String propertyName, boolean isAscending) {
        return isAscending ?
            companyTypeRepository.findAll(Sort.by(propertyName).ascending()) :
            companyTypeRepository.findAll(Sort.by(propertyName).descending());
    }

    public PageResult<CompanyType> getCompanyTypesWithPagination(Pageable pageable) {
        return new PageResult<>(pageable, companyTypeRepository.findAll(pageable));
    }

    public CompanyType getCompanyTypeById(long id) {
        return companyTypeRepository.findById(id).orElseThrow(() -> new NonExistingIdentifierException(id));
    }

    public CompanyType getCompanyTypeByCompanyId(long companyId) {
        validateParameter(companyId, companyRepository);
        return companyTypeRepository.findByCompanyId(companyId);
    }

    public List<Company> getCompaniesByCompanyType(String companyTypeName) {
        return companyTypeRepository.findCompaniesByCompanyType(companyTypeName);
    }

    public List<Employee> getEmployeesByCompanyType(String companyTypeName) {
        return companyTypeRepository.findEmployeesByCompanyType(companyTypeName);
    }

    @Transactional
    public List<CompanyType> setTestData() {
        initializeTestData();
        return getCompanyTypes();
    }

    @Transactional
    public CompanyType saveCompanyType(CompanyType companyType) {
        validateParameter(companyType);
        return companyTypeRepository.save(companyType);
    }

    @Transactional
    public CompanyType updateCompanyType(long idToUpdate, CompanyType companyType) {
        validateParameters(idToUpdate, companyType);
        return companyTypeRepository.save(companyType.createCopyWithId(idToUpdate));
    }

    @Transactional
    public void deleteCompanyTypes() {
        prepareCompaniesForRemoveCompanyTypes();
        companyTypeRepository.deleteAll();
    }

    @Transactional
    public void deleteCompanyTypeById(long id) {
        validateParameter(id, companyTypeRepository);
        prepareCompaniesForRemoveCompanyType(id);
        companyTypeRepository.deleteById(id);
    }

    // --- private methods ----------------------------------------------------

    private void validateParameters(long id, CompanyType companyType) {
        validateParameter(id, companyTypeRepository);
        validateParameter(id, companyType);
    }

    private void validateParameter(long id, JpaRepository<?, Long> repository) {
        identifierValidator.validateIdentifierExistence(repository, id);
    }

    private void validateParameter(CompanyType companyType) {
        validateParameter(companyType.getId(), companyType);
    }

    private void validateParameter(long idToUpdate, CompanyType companyType) {
        Optional<CompanyType> companyTypeOfSameName = getCompanyTypes().stream()
            .filter(savedCompanyType -> isOfSameName(savedCompanyType, companyType, idToUpdate))
            .findAny();
        if (companyTypeOfSameName.isPresent()) {
            throw new NonUniqueIdentifierException(companyType);
        }
    }

    private boolean isOfSameName(CompanyType savedCompanyType, CompanyType companyType, long idToUpdate) {
        return savedCompanyType.getName().equals(companyType.getName())
            && isNotUpdate(savedCompanyType, idToUpdate);
    }

    private boolean isNotUpdate(CompanyType savedCompanyType, long idToUpdate) {
        return savedCompanyType.getId() != idToUpdate;
    }

    private void initializeTestData() {
        initializeRepository();
        TestCompanyType.initializeList().forEach(this::saveCompanyType);
    }

    private void initializeRepository() {
        if (!getCompanyTypes().isEmpty()) {
            deleteCompanyTypes();
        }
    }

    private void prepareCompaniesForRemoveCompanyTypes() {
        getCompanyTypes().forEach(companyType -> prepareCompaniesForRemoveCompanyType(companyType.getId()));
    }

    private void prepareCompaniesForRemoveCompanyType(long companyTypeId) {
        String companyTypeName = getCompanyTypeById(companyTypeId).getName();
        getCompaniesByCompanyType(companyTypeName).forEach(this::prepareCompanyForRemoveCompanyType);
    }

    private void prepareCompanyForRemoveCompanyType(Company company) {
        company.setCompanyType(null);
        companyRepository.save(company);
    }
}