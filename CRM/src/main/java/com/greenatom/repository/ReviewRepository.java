package com.greenatom.repository;

import com.greenatom.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from review r where r.order_item_id = (select order_item_id from " +
            "order_item where product_id = ?1 )")
    List<Review> findByProduct(Long id);
}