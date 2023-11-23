package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.orderitem.OrderItemSearchCriteria;
import com.greenatom.domain.entity.OrderItem;
import com.greenatom.exception.OrderItemException;
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
public class OrderItemCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public OrderItemCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<OrderItem> findAllWithFilters(EntityPage entityPage,
                                             OrderItemSearchCriteria orderItemSearchCriteria){
        CriteriaQuery<OrderItem> criteriaQuery = criteriaBuilder.createQuery(OrderItem.class);
        Root<OrderItem> orderItemRoot = criteriaQuery.from(OrderItem.class);
        Predicate predicate = getPredicate(orderItemSearchCriteria, orderItemRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, orderItemRoot);

        TypedQuery<OrderItem> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long orderItemCount = getOrderItemCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, orderItemCount);
    }

    private Predicate getPredicate(OrderItemSearchCriteria orderItemSearchCriteria,
                                   Root<OrderItem> orderItemRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(orderItemSearchCriteria.getOrderId())){
            predicates.add(
                    criteriaBuilder.equal(orderItemRoot.get("order").get("id"),
                            orderItemSearchCriteria.getOrderId())
            );
        }
        if(Objects.nonNull(orderItemSearchCriteria.getProductId())){
            predicates.add(
                    criteriaBuilder.equal(orderItemRoot.get("product").get("id"),
                            orderItemSearchCriteria.getProductId())
            );
        }
        if(Objects.nonNull(orderItemSearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.like(orderItemRoot.get("name"),
                            "%" + orderItemSearchCriteria.getName() + "%")
            );
        }
        if(Objects.nonNull(orderItemSearchCriteria.getUnit())){
            predicates.add(
                    criteriaBuilder.like(orderItemRoot.get("unit"),
                            "%" + orderItemSearchCriteria.getUnit() + "%")
            );
        }
        if(Objects.nonNull(orderItemSearchCriteria.getCost())){
            predicates.add(
                    criteriaBuilder.equal(orderItemRoot.get("cost"),
                            orderItemSearchCriteria.getCost())
            );
        }
        if(Objects.nonNull(orderItemSearchCriteria.getOrderAmount())){
            predicates.add(
                    criteriaBuilder.equal(orderItemRoot.get("orderAmount"),
                            orderItemSearchCriteria.getOrderAmount())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<OrderItem> criteriaQuery,
                          Root<OrderItem> orderItemRoot) {
        try {
            if(entityPage.getSortDirection().equals(Sort.Direction.ASC)){
                criteriaQuery.orderBy(criteriaBuilder.asc(orderItemRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(orderItemRoot.get(entityPage.getSortBy())));
            }
        } catch (Exception e) {
            throw OrderItemException.CODE.INCORRECT_ATTRIBUTE_NAME.get();
        }
    }

    private Pageable getPageable(EntityPage entityPage) {
        Sort sort = Sort.by(entityPage.getSortDirection(), entityPage.getSortBy());
        return PageRequest.of(entityPage.getPageNumber(), entityPage.getPageSize(), sort);
    }

    private long getOrderItemCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<OrderItem> countRoot = countQuery.from(OrderItem.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
