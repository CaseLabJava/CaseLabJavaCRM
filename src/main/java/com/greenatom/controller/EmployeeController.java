package com.greenatom.controller;

import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return employeeService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.updateEmployee(employee);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.save(employee);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

}
