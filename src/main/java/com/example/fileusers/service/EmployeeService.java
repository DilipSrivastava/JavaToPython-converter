package com.example.fileusers.service;

import com.example.fileusers.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Value("${employee.file.path}")
    private String filePath;

    public void addEmployee(Employee employee) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(employee.getId() + "," + employee.getName() + "," + employee.getEmail());
            bw.newLine();
        }
    }

    public List<Employee> getAllEmployees() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines()
                     .map(line -> {
                         String[] parts = line.split(",");
                         return new Employee(Integer.parseInt(parts[0]), parts[1], parts[2]);
                     })
                     .collect(Collectors.toList());
        }
    }

    public boolean updateEmployee(Employee updatedEmployee) throws IOException {
        List<Employee> employees = getAllEmployees();
        boolean updated = false;

        for (Employee employee : employees) {
            if (employee.getId() == updatedEmployee.getId()) {
                employee.setName(updatedEmployee.getName());
                employee.setEmail(updatedEmployee.getEmail());
                updated = true;
                break;
            }
        }

        if (updated) {
            writeEmployee(employees);
        }
        return updated;
    }

    public boolean deleteEmployee(int id) throws IOException {
        List<Employee> employees = getAllEmployees();
        boolean removed = employees.removeIf(u -> u.getId() == id);

        if (removed) {
            writeEmployee(employees);
        }
        return removed;
    }

    private void writeEmployee(List<Employee> employees) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                bw.write(employee.getId() + "," + employee.getName() + "," + employee.getEmail());
                bw.newLine();
            }
        }
    }
}
