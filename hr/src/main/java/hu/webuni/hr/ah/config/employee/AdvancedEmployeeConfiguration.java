package hu.webuni.hr.ah.config.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ah.service.employee.AdvancedEmployeeService;
import hu.webuni.hr.ah.service.employee.EmployeeService;

@Configuration
@Profile("advanced")
public class AdvancedEmployeeConfiguration implements EmployeeConfiguration {

    @Bean
    @Override
    public EmployeeService employeeService() {
        return new AdvancedEmployeeService();
    }
}