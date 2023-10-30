package com.greenatom.controller;

import com.greenatom.controller.api.EmployeeApi;
import com.greenatom.domain.dto.EmployeeCleanDTO;
import com.greenatom.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @version 1.0
 * @author Максим Быков
 */
@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController implements EmployeeApi {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public EmployeeCleanDTO getEmployee(@PathVariable Long id) {
        return employeeService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping(produces = {"application/json"})
    public List<EmployeeCleanDTO> getAllEmployees(@Param("position") Integer pagePosition,
                                                  @Param("length") Integer pageLength) {
        return employeeService.findAll(pagePosition, pageLength);
    }

    @PutMapping(produces = {"application/json"})
    public EmployeeCleanDTO updateEmployee(@RequestBody EmployeeCleanDTO employee) {
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping(value = "/{id}",
            produces = {"application/json"})
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
