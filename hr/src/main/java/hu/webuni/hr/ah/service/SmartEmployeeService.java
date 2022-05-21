package hu.webuni.hr.ah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ah.config.HrConfigurationProperties;
import hu.webuni.hr.ah.config.HrConfigurationProperties.Limit;
import hu.webuni.hr.ah.config.HrConfigurationProperties.Percent;
import hu.webuni.hr.ah.config.HrConfigurationProperties.Smart;
import hu.webuni.hr.ah.model.Employee;

@Service
public class SmartEmployeeService implements EmployeeService {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private HrConfigurationProperties configurationProperties;

    // --- getters and setters ------------------------------------------------

    public HrConfigurationProperties getConfigurationProperties() { return configurationProperties; }

    // --- public methods -----------------------------------------------------

    @Override
    public int getPayRaisePercent(Employee employee) {
        Smart smartPayRaise = configurationProperties.getPayRaise().getSmartConfig();
        Limit payRaiseLimit = smartPayRaise.getLimit();
        Percent payRaisePercent = smartPayRaise.getPercent();

        if (isEmploymentLongerThanGivenYears(employee, payRaiseLimit.getHighValue())) {
            return payRaisePercent.getHighValue();
        } else if (isEmploymentLongerThanGivenYears(employee, payRaiseLimit.getMiddleValue())) {
            return payRaisePercent.getMiddleValue();
        } else if (isEmploymentLongerThanGivenYears(employee, payRaiseLimit.getLowValue())) {
            return payRaisePercent.getLowValue();
        }
        return payRaisePercent.getBaseValue();
    }
}