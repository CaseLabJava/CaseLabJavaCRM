package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.client.ClientSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.entity.Client;
import com.greenatom.exception.ClientException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ClientCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ClientCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Client> findAllWithFilters(EntityPage entityPage,
                                           ClientSearchCriteria clientSearchCriteria){
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> clientRoot = criteriaQuery.from(Client.class);
        Predicate predicate = getPredicate(clientSearchCriteria, clientRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, clientRoot);

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long clientCount = getClientCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, clientCount);
    }

    private Predicate getPredicate(ClientSearchCriteria clientSearchCriteria,
                                   Root<Client> clientRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(clientSearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.like(clientRoot.get("firstname"),
                            "%" + clientSearchCriteria.getName() + "%")
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getSurname())){
            predicates.add(
                    criteriaBuilder.like(clientRoot.get("surname"),
                            "%" + clientSearchCriteria.getSurname() + "%")
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getPatronymic())){
            predicates.add(
                    criteriaBuilder.like(clientRoot.get("patronymic"),
                            "%" + clientSearchCriteria.getPatronymic() + "%")
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getAddress())){
            predicates.add(
                    criteriaBuilder.like(clientRoot.get("address"),
                            "%" + clientSearchCriteria.getAddress() + "%")
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getEmail())){
            predicates.add(
                    criteriaBuilder.like(clientRoot.get("email"),
                            "%" + clientSearchCriteria.getEmail() + "%")
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getPhoneNumber())){
            predicates.add(
                    criteriaBuilder.like(clientRoot.get("phoneNumber"),
                            "%" + clientSearchCriteria.getPhoneNumber() + "%")
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getBank())){
            predicates.add(
                    criteriaBuilder.equal(clientRoot.get("bank"), clientSearchCriteria.getBank())
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getCompany())){
            predicates.add(
                    criteriaBuilder.like(clientRoot.get("company"),
                            "%" + clientSearchCriteria.getCompany() + "%")
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getInn())){
            predicates.add(
                    criteriaBuilder.equal(clientRoot.get("inn"), clientSearchCriteria.getInn())
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getOgrn())){
            predicates.add(
                    criteriaBuilder.equal(clientRoot.get("ogrn"), clientSearchCriteria.getOgrn())
            );
        }
        if(Objects.nonNull(clientSearchCriteria.getCorrespondentAccount())){
            predicates.add(
                    criteriaBuilder.equal(
                            clientRoot.get("correspondentAccount"), clientSearchCriteria.getCorrespondentAccount())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<Client> criteriaQuery,
                          Root<Client> clientRoot) {
        try {
            if(entityPage.getSortDirection().equals(Sort.Direction.ASC)){
                criteriaQuery.orderBy(criteriaBuilder.asc(clientRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(clientRoot.get(entityPage.getSortBy())));
            }
        } catch (Exception e) {
            throw ClientException.CODE.INCORRECT_ATTRIBUTE_NAME.get();
        }
    }

    private Pageable getPageable(EntityPage entityPage) {
        Sort sort = Sort.by(entityPage.getSortDirection(), entityPage.getSortBy());
        return PageRequest.of(entityPage.getPageNumber(), entityPage.getPageSize(), sort);
    }

    private long getClientCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Client> countRoot = countQuery.from(Client.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
