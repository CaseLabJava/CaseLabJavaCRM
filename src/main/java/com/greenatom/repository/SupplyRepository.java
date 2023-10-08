package com.greenatom.repository;

import com.greenatom.domain.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}