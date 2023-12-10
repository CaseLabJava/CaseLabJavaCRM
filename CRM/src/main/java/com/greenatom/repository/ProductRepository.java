package com.greenatom.repository;

import com.greenatom.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from product p where p.product_id = (select product_id from " +
            "order_item" +
            " where order_item_id = ?1 )")
    Product findByReviewId(Long orderItemId);

    Page<Product> findProductByProductNameContainingAndCostBefore(Pageable pageable, String name, Integer cost);
}
