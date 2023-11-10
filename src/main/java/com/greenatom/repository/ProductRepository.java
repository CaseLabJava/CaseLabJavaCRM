package com.greenatom.repository;

import com.greenatom.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findProductByProductNameContainingAndCostBefore(Pageable pageable, String name, Integer cost);
}
