package hu.webuni.hr.ah.config;

import hu.webuni.hr.ah.config.employee.EmployeeConfiguration;
import hu.webuni.hr.ah.service.employee.AdvancedEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("advanced")
class AdvancedEmployeeConfigurationIT {

    @Autowired
    EmployeeConfiguration employeeConfiguration;

    @Test
    void testEmployeeService() {
        assertThat(employeeConfiguration.employeeService()).isInstanceOf(AdvancedEmployeeService.class);
    }
}