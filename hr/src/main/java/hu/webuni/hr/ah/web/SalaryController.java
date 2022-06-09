package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private SalaryService salaryService;

    // --- public methods -----------------------------------------------------

    @PutMapping
    public Map<String, Object> getSalaryResult(@RequestBody Employee employee) {
        Map<String, Object> salaryResult = new LinkedHashMap<>();
        updateSalaryResult(salaryResult, employee);
        return salaryResult;
    }

    // --- private methods ----------------------------------------------------

    private void updateSalaryResult(Map<String, Object> salaryResult, Employee employee) {
        salaryResult.put("employee", employee);
        salaryResult.put("payRaisePercent", getPayRaisePercent(employee));
        salaryResult.put("raisedSalary", getRaisedSalary(employee));
    }

    private int getPayRaisePercent(Employee employee) {
        return salaryService.getEmployeeService().getPayRaisePercent(employee);
    }

    private int getRaisedSalary(Employee employee) {
        Employee auxEmployee = new Employee(employee);
        salaryService.setSalaryOfEmployee(auxEmployee);
        return auxEmployee.getSalary();
    }
}