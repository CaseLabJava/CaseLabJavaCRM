package com.greenatom.repository;

import com.greenatom.domain.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

   List<Order> findAllByEmployeeId(Long employeeId, PageRequest of);

   @Modifying
   @Query("UPDATE Order o SET o.linkToFolder = :linkToFolder WHERE o.id = :orderId")
   void updateLinkToFolder(@Param("orderId") Long orderId, @Param("linkToFolder") String linkToFolder);
}