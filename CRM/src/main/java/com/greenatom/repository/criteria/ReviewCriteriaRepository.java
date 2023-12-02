package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.review.ReviewSearchCriteria;
import com.greenatom.domain.entity.Review;
import com.greenatom.exception.ReviewException;
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
public class ReviewCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ReviewCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Review> findAllWithFilters(EntityPage entityPage,
                                           ReviewSearchCriteria reviewSearchCriteria){
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = criteriaQuery.from(Review.class);
        Predicate predicate = getPredicate(reviewSearchCriteria, reviewRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, reviewRoot);

        TypedQuery<Review> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long reviewCount = getReviewCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, reviewCount);
    }

    private Predicate getPredicate(ReviewSearchCriteria reviewSearchCriteria,
                                   Root<Review> reviewRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(reviewSearchCriteria.getContent())){
            predicates.add(
                    criteriaBuilder.like(reviewRoot.get("firstname"),
                            "%" + reviewSearchCriteria.getContent() + "%")
            );
        }
        if(Objects.nonNull(reviewSearchCriteria.getClientId())){
            predicates.add(
                    criteriaBuilder.like(reviewRoot.get("surname"),
                            "%" + reviewSearchCriteria.getClientId() + "%")
            );
        }


        if(Objects.nonNull(reviewSearchCriteria.getReviewStatus())){
            predicates.add(
                    criteriaBuilder.like(reviewRoot.get("address"),
                            "%" + reviewSearchCriteria.getReviewStatus() + "%")
            );
        }
        if(Objects.nonNull(reviewSearchCriteria.getReviewMark())){
            predicates.add(
                    criteriaBuilder.like(reviewRoot.get("email"),
                            "%" + reviewSearchCriteria.getReviewMark() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<Review> criteriaQuery,
                          Root<Review> reviewRoot) {
        try {
            if(entityPage.getSortDirection().equals(Sort.Direction.ASC)){
                criteriaQuery.orderBy(criteriaBuilder.asc(reviewRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(reviewRoot.get(entityPage.getSortBy())));
            }
        } catch (Exception e) {
            throw ReviewException.CODE.INCORRECT_ATTRIBUTE_NAME.get();
        }
    }

    private Pageable getPageable(EntityPage entityPage) {
        Sort sort = Sort.by(entityPage.getSortDirection(), entityPage.getSortBy());
        return PageRequest.of(entityPage.getPageNumber(), entityPage.getPageSize(), sort);
    }

    private long getReviewCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Review> countRoot = countQuery.from(Review.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
