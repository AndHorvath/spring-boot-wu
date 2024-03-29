package hu.webuni.hr.ah.service.base;

import hu.webuni.hr.ah.service.CompanyService;
import hu.webuni.hr.ah.service.CompanyTypeService;
import hu.webuni.hr.ah.service.PositionService;
import hu.webuni.hr.ah.service.employee.AbstractEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitDbService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private PositionService positionService;

    @Autowired
    private AbstractEmployeeService employeeService;

    @Autowired
    private CompanyTypeService companyTypeService;

    @Autowired
    private CompanyService companyService;

    // --- public methods -----------------------------------------------------

    public void initializeDatabase() {
        clearDatabase();
        insertTestData();
    }

    // --- private methods ----------------------------------------------------

    private void clearDatabase() {
        positionService.deletePositions();
        employeeService.deleteEmployees();
        companyTypeService.deleteCompanyTypes();
        companyService.deleteCompanies();
    }

    private void insertTestData() {
        companyService.setTestData();
    }
}