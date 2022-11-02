package hu.webuni.hr.ah.service.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ah.config.HrConfigurationProperties;
import hu.webuni.hr.ah.model.Employee;

@Service
public class DefaultEmployeeService extends AbstractEmployeeService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private HrConfigurationProperties configurationProperties;

    // --- public methods -----------------------------------------------------

    @Override
    public int getPayRaisePercent(Employee employee) {
        return configurationProperties.getPayRaise().getDefaultConfig().getPercent().getBaseValue();
    }
}