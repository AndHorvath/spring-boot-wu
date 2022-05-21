package hu.webuni.hr.ah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ah.config.HrConfigurationProperties;
import hu.webuni.hr.ah.model.Employee;

@Service
public class DefaultEmployeeService implements EmployeeService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private HrConfigurationProperties configurationProperties;

    // --- getters and setters ------------------------------------------------

    public HrConfigurationProperties getConfigurationProperties() { return configurationProperties; }

    // --- public methods -----------------------------------------------------

    @Override
    public int getPayRaisePercent(Employee employee) {
        return configurationProperties.getPayRaise().getDefaultConfig().getPercent().getBaseValue();
    }
}