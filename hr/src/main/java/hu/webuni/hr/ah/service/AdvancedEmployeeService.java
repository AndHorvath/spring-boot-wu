package hu.webuni.hr.ah.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ah.config.HrConfigurationProperties;
import hu.webuni.hr.ah.model.Employee;

@Service
public class AdvancedEmployeeService implements EmployeeService {
	
	// --- attributes ---------------------------------------------------------
	
	@Autowired
	private HrConfigurationProperties configurationProperties;
	
	// --- getters and setters ------------------------------------------------
	
	public HrConfigurationProperties getConfigurationProperties() { return configurationProperties; }
	
	// --- public methods -----------------------------------------------------
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		List<Double> limits = getLimits();
		List<Integer> percents = getPercents();
		validateConfigurationProperies(limits, percents);
		
		Map<Double, Integer> percentsToLimits = createPercentsToLimits(limits, percents);
		int baseValue = getBaseValue(percents);
		for (Double limit : percentsToLimits.keySet()) {
			if (isEmploymentLongerThanGivenYears(employee, limit)) {
				return percentsToLimits.get(limit);
			}
		}
		return baseValue;
	}
	
	// --- private methods ----------------------------------------------------
	
	private List<Double> getLimits() {
		return configurationProperties.getPayRaise().getAdvancedConfig().getLimit().getArbitraryValues();
	}
	
	private List<Integer> getPercents() {
		return configurationProperties.getPayRaise().getAdvancedConfig().getPercent().getArbitraryValues();
	}
	
	private void validateConfigurationProperies(List<Double> limits, List<Integer> percents) {
		if (percents.size() != limits.size() + 1) {
			throw new IllegalStateException(
				"Number of percentage values must be equal to number of limit values plus 1."
			);
		}
	}
	
	private Map<Double, Integer> createPercentsToLimits(List<Double> limits, List<Integer> percents) {
		Map<Double, Integer> percentsToLimits = new TreeMap<>(Comparator.reverseOrder());
		for (int i = 0; i < limits.size(); i++) {
			percentsToLimits.put(limits.get(i), percents.get(i));
		}
		return percentsToLimits;
	}
	
	private int getBaseValue(List<Integer> percents) {
		return percents.get(percents.size() - 1);
	}
}