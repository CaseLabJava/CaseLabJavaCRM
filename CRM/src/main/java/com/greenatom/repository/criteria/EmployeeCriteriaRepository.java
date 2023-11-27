package com.greenatom.repository.criteria;

import com.greenatom.domain.dto.employee.EmployeeSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.enums.JobPosition;
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
public class EmployeeCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployeeCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Employee> findAllWithFilters(EntityPage entityPage,
                                             EmployeeSearchCriteria employeeSearchCriteria){
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate predicate = getPredicate(employeeSearchCriteria, employeeRoot);
        criteriaQuery.where(predicate);
        setOrder(entityPage, criteriaQuery, employeeRoot);

        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(entityPage.getPageNumber() * entityPage.getPageSize());
        typedQuery.setMaxResults(entityPage.getPageSize());

        Pageable pageable = getPageable(entityPage);

        long employeesCount = getEmployeesCount();

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(EmployeeSearchCriteria employeeSearchCriteria,
                                   Root<Employee> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(employeeSearchCriteria.getFirstname())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("firstname"),
                            "%" + employeeSearchCriteria.getFirstname() + "%")
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getSurname())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("surname"),
                            "%" + employeeSearchCriteria.getSurname() + "%")
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getPatronymic())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("patronymic"),
                            "%" + employeeSearchCriteria.getPatronymic() + "%")
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getAddress())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("address"),
                            "%" + employeeSearchCriteria.getAddress() + "%")
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getEmail())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("email"),
                            "%" + employeeSearchCriteria.getEmail() + "%")
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getPhoneNumber())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("phoneNumber"),
                            "%" + employeeSearchCriteria.getPhoneNumber() + "%")
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getSalary())){
            predicates.add(
                    criteriaBuilder.equal(employeeRoot.get("salary"), employeeSearchCriteria.getSalary())
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getUsername())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("username"),
                            "%" + employeeSearchCriteria.getUsername() + "%")
            );
        }
        if(Objects.nonNull(employeeSearchCriteria.getJobPosition()) && !employeeSearchCriteria.getJobPosition().isEmpty()) {
            try {
                JobPosition.valueOf(employeeSearchCriteria.getJobPosition());
            } catch (IllegalArgumentException e) {
                throw EmployeeException.CODE.INCORRECT_JOB_POSITION.get();
            }
            predicates.add(
                    criteriaBuilder.equal(employeeRoot.get("jobPosition"), JobPosition.valueOf(employeeSearchCriteria.getJobPosition()))
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EntityPage entityPage,
                          CriteriaQuery<Employee> criteriaQuery,
                          Root<Employee> employeeRoot) {
        try {
            if(entityPage.getSortDirection().equals(Sort.Direction.ASC)){
                criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(entityPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(entityPage.getSortBy())));
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