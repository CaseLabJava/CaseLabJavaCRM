package com.greenatom.domain.entity;

import com.greenatom.domain.enums.JobPosition;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * A Employee.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "employee_ID")
    private Long id;


    @Column(name = "name", length = 50)
    @NonNull
    private String name;


    @Column(name = "surname", length = 50)
    @NonNull
    private String surname;


    @Column(name = "patronymic", length = 50)
    private String patronymic;


    @Column(name = "job_position")
    @NonNull
    private JobPosition jobPosition;

    @Column(name = "salary")
        private String salary;


    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    @NonNull
    private String phoneNumber;
}
