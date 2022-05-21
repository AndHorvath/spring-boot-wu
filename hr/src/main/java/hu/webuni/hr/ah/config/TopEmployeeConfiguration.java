package hu.webuni.hr.ah.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ah.service.EmployeeService;
import hu.webuni.hr.ah.service.TopEmployeeService;

@Configuration
@Profile("top")
public class TopEmployeeConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new TopEmployeeService();
    }
}