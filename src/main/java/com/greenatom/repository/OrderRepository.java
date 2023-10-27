package com.greenatom.repository;

import com.greenatom.domain.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

   List<Order> findAllByEmployeeId(Long employeeId, PageRequest of);
}