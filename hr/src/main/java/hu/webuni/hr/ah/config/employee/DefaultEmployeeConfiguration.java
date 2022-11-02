package hu.webuni.hr.ah.config.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ah.service.employee.DefaultEmployeeService;
import hu.webuni.hr.ah.service.employee.EmployeeService;

@Configuration
@Profile("!smart & !advanced & !top")
public class DefaultEmployeeConfiguration implements EmployeeConfiguration {

    @Bean
    @Override
    public EmployeeService employeeService() {
        return new DefaultEmployeeService();
    }
}