package hu.webuni.hr.ah.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ah.service.EmployeeService;
import hu.webuni.hr.ah.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartEmployeeConfiguration implements EmployeeConfiguration {

    @Bean
    @Override
    public EmployeeService employeeService() {
        return new SmartEmployeeService();
    }
}