package com.greenatom.clientselfservice.repository;

import com.greenatom.clientselfservice.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByEmail(String email);
}
