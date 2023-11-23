package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.order.OrderSearchCriteria;
import com.greenatom.domain.entity.Order;
import com.greenatom.domain.enums.DeliveryType;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.exception.OrderException;
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
public class OrderCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public OrderCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Order> findAllWithFilters(EntityPage entityPage,
                                          OrderSearchCriteria orderSearchCriteria) {
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Predicate predicate = getPredicate(orderSearchCriteria, orderRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, orderRoot);

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long ordersCount = getOrdersCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, ordersCount);
    }

    private Predicate getPredicate(OrderSearchCriteria orderSearchCriteria,
                                   Root<Order> orderRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(orderSearchCriteria.getLinkToFolder())) {
            predicates.add(
                    criteriaBuilder.like(orderRoot.get("linkToFolder"),
                            "%" + orderSearchCriteria.getLinkToFolder() + "%")
            );
        }
        if (Objects.nonNull(orderSearchCriteria.getOrderDate())) {
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("orderDate"),
                            orderSearchCriteria.getOrderDate())
            );
        }
        if (Objects.nonNull(orderSearchCriteria.getOrderStatus()) && !orderSearchCriteria.getOrderStatus().isEmpty()) {
            try {
                OrderStatus.valueOf(orderSearchCriteria.getOrderStatus());
            } catch (IllegalArgumentException e) {
                throw OrderException.CODE.INVALID_ORDER.get();
            }
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("orderStatus"),
                            OrderStatus.valueOf(orderSearchCriteria.getOrderStatus()))
            );
        }
        if (Objects.nonNull(orderSearchCriteria.getDeliveryType()) && !orderSearchCriteria.getDeliveryType().isEmpty()) {
            try {
                DeliveryType.valueOf(orderSearchCriteria.getDeliveryType());
            } catch (IllegalArgumentException e) {
                throw OrderException.CODE.INCORRECT_DELIVERY_TYPE.get();
            }
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("deliveryType"),
                            DeliveryType.valueOf(orderSearchCriteria.getDeliveryType()))
            );
        }
        if (Objects.nonNull(orderSearchCriteria.getEmployee())) {
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("employee").get("id"),
                            orderSearchCriteria.getEmployee())
            );
        }
        if (Objects.nonNull(orderSearchCriteria.getClient())) {
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("client").get("id"),
                            orderSearchCriteria.getClient())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<Order> criteriaQuery,
                          Root<Order> orderRoot) {
        try {
            if (entityPage.getSortDirection().equals(Sort.Direction.ASC)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(orderRoot.get(entityPage.getSortBy())));
            }
        } catch (Exception e) {
            throw OrderException.CODE.INCORRECT_ATTRIBUTE_NAME.get(entityPage.getSortBy());
        }
    }

    private Pageable getPageable(EntityPage entityPage) {
        Sort sort = Sort.by(entityPage.getSortDirection(), entityPage.getSortBy());
        return PageRequest.of(entityPage.getPageNumber(), entityPage.getPageSize(), sort);
    }

    private long getOrdersCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> countRoot = countQuery.from(Order.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
