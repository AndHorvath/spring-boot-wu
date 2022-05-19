package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestEmployee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTLController {

    // --- attributes ---------------------------------------------------------

    private List<Employee> employees;

    // --- constructors -------------------------------------------------------

    public EmployeeTLController() {
        initializeTestData();
    }

    // --- getters and setters ------------------------------------------------

    public List<Employee> getEmployees() { return employees; }

    // --- public methods -----------------------------------------------------

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/employees")
    public String listEmployees(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        return "employees";
    }

    @PostMapping("/employees")
    public String addEmployee(Employee newEmployee) {
        employees.add(newEmployee);
        return "redirect:employees";
    }

    // --- private methods ----------------------------------------------------

    private void initializeTestData() {
        employees = TestEmployee.initializeList();
    }
}