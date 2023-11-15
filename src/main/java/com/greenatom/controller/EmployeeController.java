package com.greenatom.controller;

import com.greenatom.controller.api.EmployeeApi;
import com.greenatom.domain.dto.employee.EmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.service.EmployeeService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR')")
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.findOne(id));
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR')")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@Param("position") Integer pagePosition,
                                                     @Param("length") Integer pageLength) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.findAll(pagePosition, pageLength));
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO employee) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping(value = "/{id}",
            produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
