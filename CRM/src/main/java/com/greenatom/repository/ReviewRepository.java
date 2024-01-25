package com.greenatom.repository;

import com.greenatom.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.orderItem.id in (select oi.id from " +
            "OrderItem oi where oi.product.id = ?1 )")
    List<Review> findByProduct(Long id);
}