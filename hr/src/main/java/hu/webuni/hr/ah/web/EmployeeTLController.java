package hu.webuni.hr.ah.web;

import hu.webuni.hr.ah.model.Employee;
import hu.webuni.hr.ah.model.TestEmployee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller
public class EmployeeTLController {

    // --- attributes ---------------------------------------------------------

    private List<Employee> employees;

    // --- constructors -------------------------------------------------------

    public EmployeeTLController() {
        employees = new ArrayList<>();
    }

    // --- home endpoints -----------------------------------------------------

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // --- employee list endpoints --------------------------------------------

    @GetMapping("/employees")
    public String listEmployees(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        return "employees";
    }

    @GetMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employees.removeIf(employee -> employee.getId() == id);
        return "redirect:/employees";
    }

    @PostMapping("/employees")
    public String addEmployee(Employee newEmployee) {
        employees.add(newEmployee);
        return "redirect:employees";
    }

    // --- employee update endpoints ------------------------------------------

    @GetMapping("/employees/update/{id}")
    public String getEmployeeForUpdateById(@PathVariable long id, Map<String, Employee> model) {
        Employee employeeToUpdate = getEmployeeById(id);
        model.put("employeeToUpdate", employeeToUpdate);
        return "update";
    }

    @PostMapping("/employees/update")
    public String updateEmployeeById(Employee employeeToUpdate) {
        int indexToUpdate = getIndexByEmployeeId(employeeToUpdate.getId());
        employees.set(indexToUpdate, employeeToUpdate);
        return "redirect:/employees";
    }

    // --- employee test endpoints --------------------------------------------

    @GetMapping("/employees/test")
    public String deleteEmployees() {
        employees.clear();
        return "redirect:/employees";
    }

    @PostMapping("/employees/test")
    public String fillTestData() {
        initializeTestData();
        return "redirect:/employees";
    }

    // --- private methods ----------------------------------------------------

    private void initializeTestData() {
        employees = TestEmployee.initializeList();
    }

    private Employee getEmployeeById(long id) {
        return employees.stream()
            .filter(employee -> employee.getId() == id)
            .findAny()
            .orElseThrow(createNoEmployeeException());
    }

    private int getIndexByEmployeeId(long id) {
        return employees.indexOf(getEmployeeById(id));
    }

    private Supplier<IllegalStateException> createNoEmployeeException() {
        return () -> new IllegalStateException("No employee with specified id in memory.");
    }
}