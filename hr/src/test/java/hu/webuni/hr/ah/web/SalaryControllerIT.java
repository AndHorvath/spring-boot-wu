package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.EmployeeSalaryConditionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"top", "test"})
class SalaryControllerIT {

    private static final String BASE_URI = "/api/salary";

    @Autowired
    WebTestClient webTestClient;

    EmployeeDto employee;
    EmployeeDto employeeInvalidNameA;
    EmployeeDto employeeInvalidNameB;
    EmployeeDto employeeInvalidDateOfEntry;
    EmployeeDto employeeInvalidPositionA;
    EmployeeDto employeeInvalidPositionB;
    EmployeeDto employeeInvalidSalaryA;
    EmployeeDto employeeInvalidSalaryB;

    Object response;

    @BeforeEach
    void setUp() {
        employee = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1400);

        employeeInvalidNameA = new EmployeeDto(1, null, LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1400);
        employeeInvalidNameB = new EmployeeDto(1, "", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1400);

        employeeInvalidDateOfEntry = new EmployeeDto(1, "Employee", LocalDateTime.now().plusYears(1), "Position", 1400);

        employeeInvalidPositionA = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), null, 1400);
        employeeInvalidPositionB = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "", 1400);

        employeeInvalidSalaryA = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 0);
        employeeInvalidSalaryB = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", -100);
    }

    @Test
    void testGetSalaryResult() {
        response = checkValidPutRequestAndReturnResponse(employee);
        assertThat(((EmployeeSalaryConditionDto) response).getEmployee()).usingRecursiveComparison().isEqualTo(employee);
        assertThat(((EmployeeSalaryConditionDto) response).getPayRaisePercent()).isEqualTo(10);
        assertThat(((EmployeeSalaryConditionDto) response).getRaisedSalary()).isEqualTo(1540);
    }

    @Test
    void testGetSalaryResult_InvalidEmployeeName() {
        response = checkInvalidPutRequestAndReturnResponse(employeeInvalidNameA);
        assertThat(getInvalidFieldFromResponse(response)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(response)).isEqualTo("NotBlank");

        response = checkInvalidPutRequestAndReturnResponse(employeeInvalidNameB);
        assertThat(getInvalidFieldFromResponse(response)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(response)).isEqualTo("NotBlank");
    }

    @Test
    void testGetSalaryResult_InvalidEmployeeDateOfEntry() {
        response = checkInvalidPutRequestAndReturnResponse(employeeInvalidDateOfEntry);
        assertThat(getInvalidFieldFromResponse(response)).isEqualTo("dateOfEntry");
        assertThat(getErrorCodeFromResponse(response)).isEqualTo("Past");
    }

    @Test
    void testGetSalaryResult_InvalidEmployeePosition() {
        response = checkInvalidPutRequestAndReturnResponse(employeeInvalidPositionA);
        assertThat(getInvalidFieldFromResponse(response)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(response)).isEqualTo("NotBlank");

        response = checkInvalidPutRequestAndReturnResponse(employeeInvalidPositionB);
        assertThat(getInvalidFieldFromResponse(response)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(response)).isEqualTo("NotBlank");
    }

    @Test
    void testGetSalaryResult_InvalidEmployeeSalary() {
        response = checkInvalidPutRequestAndReturnResponse(employeeInvalidSalaryA);
        assertThat(getInvalidFieldFromResponse(response)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(response)).isEqualTo("Positive");

        response = checkInvalidPutRequestAndReturnResponse(employeeInvalidSalaryB);
        assertThat(getInvalidFieldFromResponse(response)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(response)).isEqualTo("Positive");
    }

    // --- private methods ----------------------------------------------------

    private EmployeeSalaryConditionDto checkValidPutRequestAndReturnResponse(EmployeeDto employee) {
        return webTestClient.put()
            .uri(BASE_URI).bodyValue(employee)
            .exchange()
            .expectStatus().isOk()
            .expectBody(EmployeeSalaryConditionDto.class).returnResult().getResponseBody();
    }

    private Object checkInvalidPutRequestAndReturnResponse(EmployeeDto employee) {
        return webTestClient.put()
            .uri(BASE_URI).bodyValue(employee)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(Object.class).returnResult().getResponseBody();
    }

    private String getInvalidFieldFromResponse(Object response) {
        return (String) (castResponse(response))
            .get("fieldErrors")
            .get(0)
            .get("field");
    }

    private String getErrorCodeFromResponse(Object response) {
        return (String) (castResponse(response))
            .get("fieldErrors")
            .get(0)
            .get("code");
    }

    @SuppressWarnings("unchecked")
    private LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> castResponse(Object response) {
        try {
            return  (LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>) response;
        } catch (ClassCastException exception) {
            throw new IllegalStateException("Unexpected response type");
        }
    }
}