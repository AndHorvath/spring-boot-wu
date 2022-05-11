package hu.webuni.hr.ah.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ah.config.HrConfigurationProperties;
import hu.webuni.hr.ah.model.Employee;

@Service
public class TopEmployeeService implements EmployeeService {
	
	// --- attributes ---------------------------------------------------------
	
	@Autowired
	private HrConfigurationProperties configurationProperties;
	
	// --- getters and setters ------------------------------------------------
	
	public HrConfigurationProperties getConfigurationProperties() { return configurationProperties; }
	
	// --- public methods -----------------------------------------------------

	@Override
	public int getPayRaisePercent(Employee employee) {
		Map<Double, Integer> percentsToLimits = getPercentsToLimits();
		for (double limit : percentsToLimits.keySet()) {
			if (isEmploymentLongerThanGivenYears(employee, limit)) {
				return percentsToLimits.get(limit);
			}
		}
		return 0;
	}
	
	// --- private methods ----------------------------------------------------
	
	private Map<Double, Integer> getPercentsToLimits() {
		return configurationProperties.getPayRaise().getTopConfig().getPercentsToLimits();
	}
}