package com.greenatom.repository;

import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    List<Optional<Employee>> findAllByRole(Role role);

    @Query("SELECT COUNT (*) FROM Employee e WHERE e.username LIKE :username%")
    Integer countByUsername(String username);
}
