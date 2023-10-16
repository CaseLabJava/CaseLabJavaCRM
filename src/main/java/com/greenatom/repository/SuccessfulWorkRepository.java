package com.greenatom.repository;

import com.greenatom.domain.entity.SuccessfulWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessfulWorkRepository extends JpaRepository<SuccessfulWork, Long> {
}