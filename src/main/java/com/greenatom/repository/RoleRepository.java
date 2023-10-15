package com.greenatom.repository;

import com.greenatom.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByName(String name);
}
