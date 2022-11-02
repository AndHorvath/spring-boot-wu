package hu.webuni.hr.ah.config.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ah.service.employee.EmployeeService;
import hu.webuni.hr.ah.service.employee.TopEmployeeService;

@Configuration
@Profile("top")
public class TopEmployeeConfiguration implements EmployeeConfiguration {

    @Bean
    @Override
    public EmployeeService employeeService() {
        return new TopEmployeeService();
    }
}