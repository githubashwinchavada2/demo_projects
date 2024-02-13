package com.springboot.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.demo.entity.Employee;
import com.springboot.demo.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        employee = employeeRepository.save(employee);
        return employee;
    }

    public Employee getEmployee(String empId) {
        Employee employee = employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Could not find employee with emp id: " + empId));
        return employee;
    }

    public List<Employee> getEmployee() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find employees");
        }
        return employees;
    }
}
