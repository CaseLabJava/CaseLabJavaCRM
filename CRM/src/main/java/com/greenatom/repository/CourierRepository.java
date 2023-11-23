package com.greenatom.repository;

import com.greenatom.domain.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    Optional<Courier> findCourierByEmployeeId(Long employeeId);
    Optional<Courier> findCourierByEmployeeUsername(String username);
}
