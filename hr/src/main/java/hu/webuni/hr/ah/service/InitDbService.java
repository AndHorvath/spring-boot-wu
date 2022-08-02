package hu.webuni.hr.ah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitDbService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private AbstractEmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    // --- public methods -----------------------------------------------------

    public void initializeDatabase() {
        clearDatabase();
        insertTestData();
    }

    // --- private methods ----------------------------------------------------

    private void clearDatabase() {
        employeeService.deleteEmployees();
        companyService.deleteCompanies();
    }

    private void insertTestData() {
        companyService.getTestData();
    }
}