package com.greenatom.controller;

import com.greenatom.controller.api.EmployeeApi;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.dto.employee.EmployeeSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.service.EmployeeService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Этот код является контроллером API для управления сотрудниками. Он содержит GET и PUT методы, которые
 * позволяют выполнять следующие операции:
 *
 * <p>– GET /get/{id} получает информацию о сотруднике с заданным идентификатором;
 * <p>– PUT /update обновляет информацию о сотруднике, используя данные из тела запроса;
 * <p>– POST /add добавляет нового сотрудника, используя данные из тела запроса.
 *
 * <p>Эти методы используют сервис EmployeeService для выполнения операций с базой данных.
 * Метод DELETE /delete/{id} удаляет сотрудника с заданным идентификатором.
 *
 * <p>Эти операции выполняются при помощи сервиса ClientService, реализующего бизнес-логику.
 *
 * @author Максим Быков
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/employees")

public class EmployeeController implements EmployeeApi {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.findOne(id));
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(defaultValue = "0")
            @Min(value = 0)
            Integer pagePosition,
            @RequestParam(defaultValue = "10")
            @Min(value = 1)
            Integer pageSize,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Long salary,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String jobPosition,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.findAll(
                        new EntityPage(pagePosition, pageSize, sortDirection, sortBy),
                        new EmployeeSearchCriteria(
                                0L,
                                firstname,
                                surname,
                                patronymic,
                                username,
                                jobPosition,
                                salary,
                                email,
                                phoneNumber,
                                address)));
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeSearchCriteria employee) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping(value = "/{id}",
            produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/me")
    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN'," +
            " 'ROLE_MANAGER', 'ROLE_SPECIALIST'," +
            " 'ROLE_WAREHOUSE_WORKER', 'ROLE_COURIER')")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeInfo(Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.findEmployeeByUsername(principal.getName()));
    }
}
