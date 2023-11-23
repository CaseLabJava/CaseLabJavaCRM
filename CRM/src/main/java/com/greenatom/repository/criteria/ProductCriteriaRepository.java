package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.product.ProductSearchCriteria;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.Product;
import com.greenatom.exception.EmployeeException;
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
public class ProductCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Product> findAllWithFilters(EntityPage entityPage,
                                            ProductSearchCriteria productSearchCriteria){
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Predicate predicate = getPredicate(productSearchCriteria, productRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, productRoot);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long employeesCount = getEmployeesCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(ProductSearchCriteria productSearchCriteria,
                                   Root<Product> productRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(productSearchCriteria.getProductName())){
            predicates.add(
                    criteriaBuilder.like(productRoot.get("productName"),
                            "%" + productSearchCriteria.getProductName() + "%")
            );
        }
        if(Objects.nonNull(productSearchCriteria.getUnit())){
            predicates.add(
                    criteriaBuilder.like(productRoot.get("unit"),
                            "%" + productSearchCriteria.getUnit() + "%")
            );
        }
        if(Objects.nonNull(productSearchCriteria.getStorageAmount())){
            predicates.add(
                    criteriaBuilder.equal(productRoot.get("storageAmount"), productSearchCriteria.getStorageAmount())
            );
        }
        if(Objects.nonNull(productSearchCriteria.getCost())){
            predicates.add(
                    criteriaBuilder.equal(productRoot.get("cost"), productSearchCriteria.getCost())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<Product> criteriaQuery,
                          Root<Product> productRoot) {
        try {
            if(entityPage.getSortDirection().equals(Sort.Direction.ASC)){
                criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(productRoot.get(entityPage.getSortBy())));
            }
        } catch (Exception e) {
            throw EmployeeException.CODE.INCORRECT_ATTRIBUTE_NAME.get();
        }
    }

    private Pageable getPageable(EntityPage entityPage) {
        Sort sort = Sort.by(entityPage.getSortDirection(), entityPage.getSortBy());
        return PageRequest.of(entityPage.getPageNumber(), entityPage.getPageSize(), sort);
    }

    private long getEmployeesCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
