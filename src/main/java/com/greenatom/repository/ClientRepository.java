package com.greenatom.repository;

import com.greenatom.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findClientsByCompanyContainingAndNameContainingAndSurnameContainingAndPatronymicContaining
            (Pageable pageable, String company, String firstName, String secondName, String patronymic);
}
