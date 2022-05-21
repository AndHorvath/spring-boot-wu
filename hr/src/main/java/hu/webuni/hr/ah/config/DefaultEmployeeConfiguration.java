package hu.webuni.hr.ah.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ah.service.DefaultEmployeeService;
import hu.webuni.hr.ah.service.EmployeeService;

@Configuration
@Profile("!smart & !advanced & !top")
public class DefaultEmployeeConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new DefaultEmployeeService();
    }
}