package hu.webuni.hr.ah.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ah.service.EmployeeService;
import hu.webuni.hr.ah.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartEmloyeeConfiguration {
	
	@Bean
	public EmployeeService employeeService() {
		return new SmartEmployeeService();
	}
}