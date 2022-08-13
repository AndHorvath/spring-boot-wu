package hu.webuni.hr.ah.web;

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
class EmployeeControllerIT {

    private static final String BASE_URI = "/api/employees";

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

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

    EmployeeDto dummy;

    EmployeeDto response;
    List<EmployeeDto> responseList;
    Object responseMap;

    @BeforeEach
    void setUp() {
        employee = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000, null);

        employeeInvalidNameA = new EmployeeDto(1, null, LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000, null);
        employeeInvalidNameB = new EmployeeDto(1, "", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 1000, null);

        employeeInvalidDateOfEntry = new EmployeeDto(1, "Employee", LocalDateTime.now().plusYears(1), "Position", 1000, null);

        employeeInvalidPositionA = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), null, 1000, null);
        employeeInvalidPositionB = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "", 1000, null);

        employeeInvalidSalaryA = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", 0, null);
        employeeInvalidSalaryB = new EmployeeDto(1, "Employee", LocalDateTime.of(2010, 10, 20, 0, 0), "Position", -100, null);

        dummy = new EmployeeDto(0, "D", LocalDateTime.of(1, 1, 1, 1, 1), "D", 1, null);
    }

    @Test
    void testGetEmployees() {
        clearAndLoadDummyEmployeeForId(0);

        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testGetEmployeeById() {
        clearAndLoadDummyEmployeeForId(0);

        response = checkValidGetRequestAndReturnResponse("/0");
        assertThat(response).usingRecursiveComparison().isEqualTo(dummy);
    }

    @Test
    void testGetEmployeeById_InvalidId() {
        clearAndLoadDummyEmployeeForId(1);

        responseMap = checkInvalidGetRequestAndReturnResponseMap("/0");
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("No entry with specified ID in database: 0");
    }

    @Test
    void testGetEmployeesOverSalaryLimit_ConditionNotSatisfied() {
        clearAndLoadDummyEmployeeForId(0);

        responseList = checkValidGetRequestAndReturnResponseList("?salaryLimit=1");
        assertThat(responseList).isEqualTo(List.of());
    }

    @Test
    void testGetEmployeesOverSalaryLimit_ConditionSatisfied() {
        dummy.setSalary(2);
        clearAndLoadDummyEmployeeForId(0);

        responseList = checkValidGetRequestAndReturnResponseList("?salaryLimit=1");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testAddEmployee() {
        clearAndLoadDummyEmployeeForId(0);

        response = checkValidPostRequestAndReturnResponse(employee);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(response).usingRecursiveComparison().isEqualTo(employee);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy, employee));
    }

    @Test
    void testAddEmployee_ExistingId() {
        clearAndLoadDummyEmployeeForId(1);

        response = checkValidPostRequestAndReturnResponse(employee);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(response).usingRecursiveComparison().isEqualTo(employee);
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(employee));
    }

    @Test
    void testAddEmployee_InvalidName() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(employeeInvalidNameA);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(employeeInvalidNameB);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testAddEmployee_InvalidDateOfEntry() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(employeeInvalidDateOfEntry);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("dateOfEntry");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Past");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testAddEmployee_InvalidPosition() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(employeeInvalidPositionA);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(employeeInvalidPositionB);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testAddEmployee_InvalidSalary() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPostRequestAndReturnResponseMap(employeeInvalidSalaryA);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));

        responseMap = checkInvalidPostRequestAndReturnResponseMap(employeeInvalidSalaryB);
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testUpdateEmployee() {
        clearAndLoadDummyEmployeeForId(2);

        response = checkValidPutRequestAndReturnResponse(employee, "/2");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(response.getId()).isEqualTo(2);
        assertThat(response).usingRecursiveComparison().ignoringFields("id").isEqualTo(employee);
        assertThat(responseList).usingRecursiveComparison().ignoringFields("id").isEqualTo(List.of(employee));
    }

    @Test
    void testUpdateEmployee_InvalidId() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(NOT_FOUND, employee, "/1");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getErrorFromResponse(responseMap)).isEqualTo("No entry with specified ID in database: 1");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testUpdateEmployee_InvalidName() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidNameA, "/0");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));

        responseMap =  checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidNameB, "/0");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("name");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testUpdateEmployee_InvalidDateOfEntry() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidDateOfEntry, "/0");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("dateOfEntry");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Past");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testUpdateEmployee_InvalidPosition() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidPositionA, "/0");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidPositionB, "/0");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("position");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("NotBlank");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testUpdateEmployee_InvalidSalary() {
        clearAndLoadDummyEmployeeForId(0);

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidSalaryA, "/0");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));

        responseMap = checkInvalidPutRequestAndReturnResponseMap(BAD_REQUEST, employeeInvalidSalaryB, "/0");
        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(getInvalidFieldFromResponse(responseMap)).isEqualTo("salary");
        assertThat(getErrorCodeFromResponse(responseMap)).isEqualTo("Positive");
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(dummy));
    }

    @Test
    void testDeleteEmployees() {
        clearAndLoadDummyEmployeeForId(0);
        checkValidPostRequestAndReturnResponse(employee);
        checkValidDeleteRequest();

        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(responseList).isEqualTo(List.of());
    }

    @Test
    void testDeleteEmployeeById() {
        clearAndLoadDummyEmployeeForId(0);
        checkValidPostRequestAndReturnResponse(employee);
        checkValidDeleteRequest("/0");

        responseList = checkValidGetRequestAndReturnResponseList();
        assertThat(responseList).usingRecursiveComparison().isEqualTo(List.of(employee));
    }

    // --- private methods checking get request -------------------------------

    private EmployeeDto checkValidGetRequestAndReturnResponse(String uriExtension) {
        return webTestClient.get()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isOk()
            .expectBody(EmployeeDto.class).returnResult().getResponseBody();
    }

    private List<EmployeeDto> checkValidGetRequestAndReturnResponseList() {
        return checkValidGetRequestAndReturnResponseList("");
    }

    private List<EmployeeDto> checkValidGetRequestAndReturnResponseList(String uriExtension) {
        return webTestClient.get()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
    }

    private Object checkInvalidGetRequestAndReturnResponseMap(String uriExtension) {
        return webTestClient.get()
            .uri(BASE_URI + uriExtension)
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(Object.class).returnResult().getResponseBody();
    }

    // --- private methods checking post request ------------------------------

    private EmployeeDto checkValidPostRequestAndReturnResponse(EmployeeDto employee) {
        return webTestClient.post()
            .uri(BASE_URI).bodyValue(employee)
            .exchange()
            .expectStatus().isOk()
            .expectBody(EmployeeDto.class).returnResult().getResponseBody();
    }

    private Object checkInvalidPostRequestAndReturnResponseMap(EmployeeDto employee) {
        return webTestClient.post()
            .uri(BASE_URI).bodyValue(employee)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(Object.class).returnResult().getResponseBody();
    }

    // --- private methods checking put request -------------------------------

    private EmployeeDto checkValidPutRequestAndReturnResponse(EmployeeDto employee, String uriExtension) {
        return webTestClient.put()
            .uri(BASE_URI + uriExtension).bodyValue(employee)
            .exchange()
            .expectStatus().isOk()
            .expectBody(EmployeeDto.class).returnResult().getResponseBody();
    }

    private Object checkInvalidPutRequestAndReturnResponseMap(HttpStatus status, EmployeeDto employee, String uriExtension) {
        if (status == NOT_FOUND) {
            return webTestClient.put()
                .uri(BASE_URI + uriExtension).bodyValue(employee)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else if (status == BAD_REQUEST) {
            return webTestClient.put()
                .uri(BASE_URI + uriExtension).bodyValue(employee)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Object.class).returnResult().getResponseBody();
        } else {
            throw new IllegalArgumentException("Invalid status parameter");
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

    private void clearAndLoadDummyEmployeeForId(long id) {
        EmployeeDto dummyEmployee = new EmployeeDto(
            id, dummy.getName(), dummy.getDateOfEntry(), dummy.getPosition(), dummy.getSalary(), null
        );
        webTestClient.delete().uri(BASE_URI).exchange();
        webTestClient.post().uri(BASE_URI).bodyValue(dummyEmployee).exchange();
    }

    @SuppressWarnings("unchecked")
    private LinkedHashMap<String, Object> castResponseShallow(Object responseMap) {
        try {
            return  (LinkedHashMap<String, Object>) responseMap;
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