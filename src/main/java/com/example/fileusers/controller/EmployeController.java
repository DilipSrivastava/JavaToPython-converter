package com.example.fileusers.controller;

import com.example.fileusers.model.Employee;
import com.example.fileusers.model.User;
import com.example.fileusers.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public String addEmployee(@RequestBody Employee employee) throws IOException {
        employeeService.addEmployee(employee);
        return "Employee added!";
    }

    @GetMapping
    public List<Employee> getAllEmployees() throws IOException {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody Employee employee) throws IOException {
        employee.setId(id);
        return employeeService.updateEmployee(employee) ? "Employee updated!" : "Employee not found!";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) throws IOException {
        return employeeService.deleteEmployee(id) ? "Employee deleted!" : "Employee not found!";
    }
}
