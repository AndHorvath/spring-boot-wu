package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.dto.CompanyDto;
import hu.webuni.hr.ah.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyControllerIT {

    private final String BASE_URI = "/api/companies";
    private final String COMPLETE_VIEW = "?full=true";
    private final String BASE_VIEW = "?full=false";

    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final HttpStatus SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    @Autowired
    WebTestClient webTestClient;

    CompanyDto company;
    CompanyDto companyOfSameRegistrationNumber;
    CompanyDto companyInvalidRegistrationNumberA;
    CompanyDto companyInvalidRegistrationNumberB;
    CompanyDto companyInvalidEmployeeNameA;
    CompanyDto companyInvalidEmployeeNameB;
    CompanyDto companyInvalidEmployeeDateOfEntry;
    CompanyDto companyInvalidEmployeePositionA;
    CompanyDto companyInvalidEmployeePositionB;
    CompanyDto companyInvalidEmployeeSalaryA;
    CompanyDto companyInvalidEmployeeSalaryB;

    EmployeeDto employee;
    EmployeeDto employeeInvalidNameA;
    EmployeeDto employeeInvalidNameB;
    EmployeeDto employeeInvalidDateOfEntry;
    EmployeeDto employeeInvalidPositionA;
    EmployeeDto employeeInvalidPositionB;
    EmployeeDto employeeInvalidSalaryA;
    EmployeeDto employeeInvalidSalaryB;

    EmployeeDto dummyEmployee;
    CompanyDto dummyCompany;
    CompanyDto dummyCompanyBaseData;

    CompanyDto response;
    List<CompanyDto> responseList;
    Object responseMap;

    @BeforeEach
    void setUp() {
        employee = new EmployeeDto(1L, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000);
        employeeInvalidNameA = new EmployeeDto(1L, null, LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000);
        employeeInvalidNameB = new EmployeeDto(1L, "", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000);
        employeeInvalidDateOfEntry = new EmployeeDto(1L, "Employee", LocalDateTime.now().plusYears(1), "Position", 1000);
        employeeInvalidPositionA = new EmployeeDto(1L, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), null, 1000);
        employeeInvalidPositionB = new EmployeeDto(1L, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "", 1000);
        employeeInvalidSalaryA = new EmployeeDto(1L, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 0);
        employeeInvalidSalaryB = new EmployeeDto(1L, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", -100);

        company = new CompanyDto(1, "AA-11", "Company", "Address", List.of(employee));

        companyInvalidRegistrationNumberA = new CompanyDto(1, "AA-1", "Company", "Address", List.of(employee));
        companyInvalidRegistrationNumberB = new CompanyDto(1, "AA-111", "Company", "Address", List.of(employee));

        companyInvalidEmployeeNameA = createCompanyWithEmployees(company, List.of(employeeInvalidNameA));
        companyInvalidEmployeeNameB = createCompanyWithEmployees(company, List.of(employeeInvalidNameB));
        companyInvalidEmployeeDateOfEntry = createCompanyWithEmployees(company, List.of(employeeInvalidDateOfEntry));
        companyInvalidEmployeePositionA = createCompanyWithEmployees(company, List.of(employeeInvalidPositionA));
        companyInvalidEmployeePositionB = createCompanyWithEmployees(company, List.of(employeeInvalidPositionB));
        companyInvalidEmployeeSalaryA = createCompanyWithEmployees(company, List.of(employeeInvalidSalaryA));
        companyInvalidEmployeeSalaryB = createCompanyWithEmployees(company, List.of(employeeInvalidSalaryB));

        dummyEmployee = new EmployeeDto(0L, "D", LocalDateTime.of(1, 1, 1, 1, 1), "D", 1);
        dummyCompany = new CompanyDto(0, "DDDDD", "D", "D", List.of(dummyEmployee));
        dummyCompanyBaseData = new CompanyDto(0, "DDDDD", "D", "D", null);
    }

    @Test
    void testGetCompanies_CompleteView() {
        clearAndLoadDummyCompanyForId(0);

        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testGetCompanies_BaseView() {
        clearAndLoadDummyCompanyForId(0);

        responseList = checkValidGetRequestAndReturnResponseList(BASE_VIEW);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompanyBaseData));
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompanyBaseData));
    }

    @Test
    void testGetCompanyById_CompleteView() {
        clearAndLoadDummyCompanyForId(0);

        response = checkValidGetRequestAndReturnResponse("/0" + COMPLETE_VIEW);
        assertThat(response).usingRecursiveComparison().isEqualTo(dummyCompany);
    }

    @Test
    void testGetCompanyById_BaseView() {
        clearAndLoadDummyCompanyForId(0);

        response = checkValidGetRequestAndReturnResponse("/0" + BASE_VIEW);
        assertThat(response).usingRecursiveComparison().isEqualTo(dummyCompanyBaseData);
        response = checkValidGetRequestAndReturnResponse("/0");
        assertThat(response).usingRecursiveComparison().isEqualTo(dummyCompanyBaseData);
    }

    @Test
    void testGetCompanyById_InvalidId() {
        clearAndLoadDummyCompanyForId(1);

        responseMap = checkInvalidGetRequestAndReturnResponseMap("/0");
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("No entry with specified ID in memory: 0");
    }

    @Test
    void testAddCompany() {
        clearAndLoadDummyCompanyForId(0);

        response = checkValidPostRequestAndReturnResponse(company);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(response).usingRecursiveComparison().isEqualTo(company);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, company));
    }

    @Test
    void testAddCompany_ExistingId() {
        clearAndLoadDummyCompanyForId(1);

        response = checkValidPostRequestAndReturnResponse(company);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(response).usingRecursiveComparison().isEqualTo(company);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(company));
    }

    @Test
    void testAddCompany_ExistingRegistrationNumberDifferentId() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(company);
        companyOfSameRegistrationNumber = new CompanyDto(2, company.getRegistrationNumber(), "C", "C", List.of());

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyOfSameRegistrationNumber);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("Specified registration number already exists: AA-11");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, company));
    }

    @Test
    void testAddCompany_ExistingRegistrationNumberIdenticalId() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(company);
        companyOfSameRegistrationNumber = new CompanyDto(1, company.getRegistrationNumber(), "C", "C", List.of());

        response = checkValidPostRequestAndReturnResponse(companyOfSameRegistrationNumber);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(response).usingRecursiveComparison().isEqualTo(companyOfSameRegistrationNumber);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, companyOfSameRegistrationNumber));
    }

    @Test
    void testAddCompany_InvalidRegistrationNumber() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidRegistrationNumberA);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("registrationNumber");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Size");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidRegistrationNumberB);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("registrationNumber");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Size");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddCompany_InvalidEmployeeName() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidEmployeeNameA);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidEmployeeNameB);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddCompany_InvalidEmployeeDateOfEntry() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidEmployeeDateOfEntry);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].dateOfEntry");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Past");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddCompany_InvalidEmployeePosition() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidEmployeePositionA);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidEmployeePositionB);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddCompany_InvalidEmployeeSalary() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidEmployeeSalaryA);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(companyInvalidEmployeeSalaryB);
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateCompany() {
        clearAndLoadDummyCompanyForId(2);

        response = checkValidPutRequestAndReturnResponse(company, "/2");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(response.getId()).isEqualTo(2);
        assertThat(response).usingRecursiveComparison().ignoringFields("id").isEqualTo(company);
        assertThat(responseList).usingRecursiveComparison().ignoringFields("id").isEqualTo(List.of(company));
    }

    @Test
    void testUpdateCompany_InvalidId() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(NOT_FOUND, company, "/1");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("No entry with specified ID in memory: 1");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateCompany_ExistingRegistrationNumberDifferentId() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(company);
        companyOfSameRegistrationNumber = new CompanyDto(0, company.getRegistrationNumber(), "C", "C", List.of());

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyOfSameRegistrationNumber, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("Specified registration number already exists: AA-11");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, company));
    }

    @Test
    void testUpdateCompany_ExistingRegistrationNumberIdenticalId() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(company);
        companyOfSameRegistrationNumber = new CompanyDto(1, company.getRegistrationNumber(), "C", "C", List.of());

        response = checkValidPutRequestAndReturnResponse(companyOfSameRegistrationNumber ,"/1");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(response).usingRecursiveComparison().isEqualTo(companyOfSameRegistrationNumber);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany, companyOfSameRegistrationNumber));
    }

    @Test
    void testUpdateCompany_InvalidRegistrationNumber() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidRegistrationNumberA, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("registrationNumber");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Size");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidRegistrationNumberB, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("registrationNumber");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Size");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateCompany_InvalidEmployeeName() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidEmployeeNameA, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidEmployeeNameB, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateCompany_InvalidEmployeeDateOfEntry() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidEmployeeDateOfEntry, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].dateOfEntry");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Past");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateCompany_InvalidEmployeePosition() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidEmployeePositionA, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidEmployeePositionB, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateCompany_InvalidEmployeeSalary() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidEmployeeSalaryA, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, companyInvalidEmployeeSalaryB, "/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("employees[0].salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testDeleteCompanies() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(company);
        checkValidDeleteRequest();

        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(responseList).isEqualTo(List.of());
    }

    @Test
    void testDeleteCompanyById() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(company);
        checkValidDeleteRequest("/0");

        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(company));
    }

    @Test
    void testAddEmployeeToCompany() {
        clearAndLoadDummyCompanyForId(0);

        response = checkValidPostRequestAndReturnResponse(employee, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(response.getEmployees()).usingRecursiveComparison().isEqualTo(List.of(dummyEmployee, employee));
        assertThat(response).usingRecursiveComparison().ignoringFields("employees").isEqualTo(dummyCompany);
        assertThat(responseList).usingRecursiveComparison().ignoringFields("employees").isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddEmployeeToCompany_InvalidCompanyId() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(NOT_FOUND, employee, "/1/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("No entry with specified ID in memory: 1");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddEmployeeToCompany_InvalidEmployeeName() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidNameA, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidNameB, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddEmployeeToCompany_InvalidEmployeeDateOfEntry() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidDateOfEntry, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("dateOfEntry");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Past");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddEmployeeToCompany_InvalidEmployeePosition() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidPositionA, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidPositionB, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testAddEmployeeToCompany_InvalidEmployeeSalary() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidSalaryA, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidSalaryB, "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateEmployeeListInCompany() {
        clearAndLoadDummyCompanyForId(0);

        response = checkValidPutRequestAndReturnResponse(List.of(employee, dummyEmployee), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(response.getEmployees()).usingRecursiveComparison().isEqualTo(List.of(employee, dummyEmployee));
        assertThat(response).usingRecursiveComparison().ignoringFields("employees").isEqualTo(dummyCompany);
        assertThat(responseList).usingRecursiveComparison().ignoringFields("employees").isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateEmployeeListInCompany_InvalidCompanyId() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(NOT_FOUND, List.of(employee), "/1/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("No entry with specified ID in memory: 1");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateEmployeeListInCompany_InvalidEmployeeName() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(SERVER_ERROR, List.of(employeeInvalidNameA), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).contains("employeeDtos[0].name");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(SERVER_ERROR, List.of(employeeInvalidNameB), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).contains("employeeDtos[0].name");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateEmployeeListInCompany_InvalidEmployeeDateOfEntry() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(SERVER_ERROR, List.of(employeeInvalidDateOfEntry), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).contains("employeeDtos[0].dateOfEntry");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateEmployeeInCompany_InvalidEmployeePosition() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(SERVER_ERROR, List.of(employeeInvalidPositionA), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).contains("employeeDtos[0].position");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(SERVER_ERROR, List.of(employeeInvalidPositionB), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).contains("employeeDtos[0].position");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testUpdateEmployeeInCompany_InvalidEmployeeSalary() {
        clearAndLoadDummyCompanyForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(SERVER_ERROR, List.of(employeeInvalidSalaryA), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).contains("employeeDtos[0].salary");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(SERVER_ERROR, List.of(employeeInvalidSalaryB), "/0/employees");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).contains("employeeDtos[0].salary");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testDeleteEmployeeInCompanyById() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(employee, "/0/employees");
        checkValidDeleteRequest("/0/employees/0");

        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(responseList.get(0).getEmployees()).usingRecursiveComparison().isEqualTo(List.of(employee));
        assertThat(responseList).usingRecursiveComparison().ignoringFields("employees").isEqualTo(List.of(dummyCompany));
    }

    @Test
    void testDeleteEmployeeInCompanyById_InvalidCompanyId() {
        clearAndLoadDummyCompanyForId(0);
        checkValidPostRequestAndReturnResponse(employee, "/0/employees");

        responseMap = checkInvalidDeleteRequestAndReturnResponse("/1/employees/0");
        responseList = checkValidGetRequestAndReturnResponseList(COMPLETE_VIEW);
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("No entry with specified ID in memory: 1");
        assertThat(responseList.get(0).getEmployees()).usingRecursiveComparison().isEqualTo(List.of(dummyEmployee, employee));
        assertThat(responseList).usingRecursiveComparison().ignoringFields("employees").isEqualTo(List.of(dummyCompany));
    }

    // --- private methods checking get request -------------------------------

    private CompanyDto checkValidGetRequestAndReturnResponse(String uriExtension) {
        return webTestClient.get()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CompanyDto.class).returnResult().getResponseBody();
    }

    private List<CompanyDto> checkValidGetRequestAndReturnResponseList() {
        return checkValidGetRequestAndReturnResponseList("");
    }

    private List<CompanyDto> checkValidGetRequestAndReturnResponseList(String uriExtension) {
        return webTestClient.get()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(CompanyDto.class).returnResult().getResponseBody();
    }

    private Object checkInvalidGetRequestAndReturnResponseMap(String uriExtension) {
        return webTestClient.get()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(Object.class).returnResult().getResponseBody();
    }

    // --- private methods checking post request ------------------------------

    private CompanyDto checkValidPostRequestAndReturnResponse(CompanyDto company) {
        return webTestClient.post()
            .uri(BASE_URI).bodyValue(company)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CompanyDto.class).returnResult().getResponseBody();
    }

    private CompanyDto checkValidPostRequestAndReturnResponse(EmployeeDto employee, String uriExtension) {
        return webTestClient.post()
            .uri(BASE_URI + uriExtension).bodyValue(employee)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CompanyDto.class).returnResult().getResponseBody();
    }

    private Object checkInvalidPostRequestAndReturnResponseMap(CompanyDto company) {
        return webTestClient.post()
            .uri(BASE_URI).bodyValue(company)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(Object.class).returnResult().getResponseBody();
    }

    private Object checkInvalidPostRequestAndReturnResponseMap(HttpStatus status, EmployeeDto employee, String uriExtension) {
        if (status == NOT_FOUND) {
            return webTestClient.post()
                .uri(BASE_URI + uriExtension).bodyValue(employee)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else if (status == BAD_REQUEST) {
            return webTestClient.post()
                .uri(BASE_URI + uriExtension).bodyValue(employee)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else {
            throw new IllegalStateException("Invalid status parameter");
        }
    }

    // --- private methods checking put request -------------------------------

    private CompanyDto checkValidPutRequestAndReturnResponse(CompanyDto company, String uriExtension) {
        return webTestClient.put()
            .uri(BASE_URI + uriExtension).bodyValue(company)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CompanyDto.class).returnResult().getResponseBody();
    }

    private CompanyDto checkValidPutRequestAndReturnResponse(List<EmployeeDto> employees, String uriExtension) {
        return webTestClient.put()
            .uri(BASE_URI + uriExtension).bodyValue(employees)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CompanyDto.class).returnResult().getResponseBody();
    }

    private Object checkInvalidPutRequestAndReturnResponseMap(HttpStatus status, CompanyDto company, String uriExtension) {
        if (status == NOT_FOUND) {
            return webTestClient.put()
                .uri(BASE_URI + uriExtension).bodyValue(company)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else if (status == BAD_REQUEST) {
            return webTestClient.put()
                .uri(BASE_URI + uriExtension).bodyValue(company)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else {
            throw new IllegalStateException("Invalid status parameter");
        }
    }

    private Object checkInvalidPutRequestAndReturnResponseMap(HttpStatus status, List<EmployeeDto> employees, String uriExtension) {
        if (status == NOT_FOUND) {
            return webTestClient.put()
                .uri(BASE_URI + uriExtension).bodyValue(employees)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else if (status == SERVER_ERROR) {
            return webTestClient.put()
                .uri(BASE_URI + uriExtension).bodyValue(employees)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else {
            throw new IllegalStateException("IInvalid status parameter");
        }
    }

    // --- private methods checking delete request ----------------------------

    private void checkValidDeleteRequest() {
        checkValidDeleteRequest("");
    }

    private void checkValidDeleteRequest(String uriExtension) {
        webTestClient.delete()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isOk();
    }

    private Object checkInvalidDeleteRequestAndReturnResponse(String uriExtension) {
        return webTestClient.delete()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(Object.class).returnResult().getResponseBody();
    }

    // --- private methods getting parts of invalid response ------------------

    private String getErrorFromResponse(Object responseMap) {
        return (String) castResponseShallow(responseMap)
            .get("message");
    }

    private String getInvalidFieldFromResponse(Object responseMap) {
        return (String) castResponseDeep(responseMap)
            .get("fieldErrors")
            .get(0)
            .get("field");
    }

    private String getErrorCodeFromResponse(Object responseMap) {
        return (String) castResponseDeep(responseMap)
            .get("fieldErrors")
            .get(0)
            .get("code");
    }

    // --- common private methods ---------------------------------------------

    private void clearAndLoadDummyCompanyForId(long id) {
        CompanyDto dummy = new CompanyDto(
            id,
            dummyCompany.getRegistrationNumber(),
            dummyCompany.getName(),
            dummyCompany.getAddress(),
            dummyCompany.getEmployees()
        );
        webTestClient.delete().uri(BASE_URI).exchange();
        webTestClient.post().uri(BASE_URI).bodyValue(dummy).exchange();
    }

    private CompanyDto createCompanyWithEmployees(CompanyDto company, List<EmployeeDto> employees) {
        return new CompanyDto(
            company.getId(), company.getRegistrationNumber(), company.getName(), company.getAddress(), employees
        );
    }

    @SuppressWarnings("unchecked")
    private LinkedHashMap<String, Object> castResponseShallow(Object responseMap) {
        try {
            return (LinkedHashMap<String, Object>) responseMap;
        } catch (ClassCastException exception) {
            throw new IllegalStateException("Unexpected response type");
        }
    }

    @SuppressWarnings("unchecked")
    private LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> castResponseDeep(Object responseMap) {
        try {
            return (LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>) responseMap;
        } catch (ClassCastException exception) {
            throw new IllegalStateException("Unexpected response type");
        }
    }
}