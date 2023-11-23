package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.delivery.DeliverySearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.entity.Delivery;
import com.greenatom.domain.enums.DeliveryStatus;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.exception.DeliveryException;
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
public class DeliveryCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public DeliveryCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Delivery> findAllWithFilters(EntityPage entityPage,
                                          DeliverySearchCriteria deliverySearchCriteria) {
        CriteriaQuery<Delivery> criteriaQuery = criteriaBuilder.createQuery(Delivery.class);
        Root<Delivery> orderRoot = criteriaQuery.from(Delivery.class);
        Predicate predicate = getPredicate(deliverySearchCriteria, orderRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, orderRoot);

        TypedQuery<Delivery> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long ordersCount = getDeliversCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, ordersCount);
    }

    private Predicate getPredicate(DeliverySearchCriteria deliverySearchCriteria,
                                   Root<Delivery> orderRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(deliverySearchCriteria.getOrderId())) {
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("order").get("id"),
                              deliverySearchCriteria.getOrderId())
            );
        }
        if (Objects.nonNull(deliverySearchCriteria.getCourierId())) {
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("courier").get("id"),
                            deliverySearchCriteria.getCourierId())
            );
        }
        if (Objects.nonNull(deliverySearchCriteria.getDeliveryStatus()) && !deliverySearchCriteria.getDeliveryStatus().isEmpty()) {
            try {
                DeliveryStatus.valueOf(deliverySearchCriteria.getDeliveryStatus());
            } catch (IllegalArgumentException e) {
                throw DeliveryException.CODE.INVALID_STATUS.get();
            }
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("deliveryStatus"),
                            OrderStatus.valueOf(deliverySearchCriteria.getDeliveryStatus()))
            );
        }
        if (Objects.nonNull(deliverySearchCriteria.getStartTime())) {
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("startTime"),
                            deliverySearchCriteria.getStartTime())
            );
        }
        if (Objects.nonNull(deliverySearchCriteria.getEndTime())) {
            predicates.add(
                    criteriaBuilder.equal(orderRoot.get("endTime"),
                            deliverySearchCriteria.getEndTime())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<Delivery> criteriaQuery,
                          Root<Delivery> deliveryRoot) {
        try {
            if (entityPage.getSortDirection().equals(Sort.Direction.ASC)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(deliveryRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(deliveryRoot.get(entityPage.getSortBy())));
            }
        } catch (Exception e) {
            throw OrderException.CODE.INCORRECT_ATTRIBUTE_NAME.get();
        }
    }

    private Pageable getPageable(EntityPage entityPage) {
        Sort sort = Sort.by(entityPage.getSortDirection(), entityPage.getSortBy());
        return PageRequest.of(entityPage.getPageNumber(), entityPage.getPageSize(), sort);
    }

    private long getDeliversCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Delivery> countRoot = countQuery.from(Delivery.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
