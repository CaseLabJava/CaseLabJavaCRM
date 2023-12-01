package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.preparing_order.PreparingOrderSearchCriteria;
import com.greenatom.domain.entity.PreparingOrder;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.domain.enums.PreparingOrderStatus;
import com.greenatom.exception.OrderException;
import com.greenatom.exception.PreparingOrderException;
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
public class PreparingOrderCriteriaRepository {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public PreparingOrderCriteriaRepository(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<PreparingOrder> findAllWithFilters(EntityPage entityPage,
                                             PreparingOrderSearchCriteria preparingOrderSearchCriteria) {
        CriteriaQuery<PreparingOrder> criteriaQuery = criteriaBuilder.createQuery(PreparingOrder.class);
        Root<PreparingOrder> orderRoot = criteriaQuery.from(PreparingOrder.class);
        Predicate predicate = getPredicate(preparingOrderSearchCriteria, orderRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, orderRoot);

        TypedQuery<PreparingOrder> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long ordersCount = getPreparingOrdersCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, ordersCount);
    }

    private Predicate getPredicate(PreparingOrderSearchCriteria preparingOrderSearchCriteria,
                                   Root<PreparingOrder> preparingOrderRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(preparingOrderSearchCriteria.getOrderId())) {
            predicates.add(
                    criteriaBuilder.equal(preparingOrderRoot.get("order").get("id"),
                            preparingOrderSearchCriteria.getOrderId())
            );
        }
        if (Objects.nonNull(preparingOrderSearchCriteria.getEmployeeId())) {
            predicates.add(
                    criteriaBuilder.equal(preparingOrderRoot.get("employee").get("id"),
                            preparingOrderSearchCriteria.getEmployeeId())
            );
        }
        if (Objects.nonNull(preparingOrderSearchCriteria.getPreparingOrderStatus()) && !preparingOrderSearchCriteria.getPreparingOrderStatus().isEmpty()) {
            try {
                PreparingOrderStatus.valueOf(preparingOrderSearchCriteria.getPreparingOrderStatus());
            } catch (IllegalArgumentException e) {
                throw PreparingOrderException.CODE.INVALID_STATUS.get();
            }
            predicates.add(
                    criteriaBuilder.equal(preparingOrderRoot.get("preparingOrderStatus"),
                            OrderStatus.valueOf(preparingOrderSearchCriteria.getPreparingOrderStatus()))
            );
        }
        if (Objects.nonNull(preparingOrderSearchCriteria.getStartTime())) {
            predicates.add(
                    criteriaBuilder.equal(preparingOrderRoot.get("startTime"),
                            preparingOrderSearchCriteria.getStartTime())
            );
        }
        if (Objects.nonNull(preparingOrderSearchCriteria.getEndTime())) {
            predicates.add(
                    criteriaBuilder.equal(preparingOrderRoot.get("endTime"),
                            preparingOrderSearchCriteria.getEndTime())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<PreparingOrder> criteriaQuery,
                          Root<PreparingOrder> deliveryRoot) {
        try {
            if (entityPage.getSortDirection().equals(Sort.Direction.ASC)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(deliveryRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(deliveryRoot.get(entityPage.getSortBy())));
            }
        } catch (Exception e) {
            throw OrderException.CODE.INCORRECT_ATTRIBUTE_NAME.get(entityPage.getSortBy());
        }
    }

    private Pageable getPageable(EntityPage entityPage) {
        Sort sort = Sort.by(entityPage.getSortDirection(), entityPage.getSortBy());
        return PageRequest.of(entityPage.getPageNumber(), entityPage.getPageSize(), sort);
    }

    private long getPreparingOrdersCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<PreparingOrder> countRoot = countQuery.from(PreparingOrder.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
