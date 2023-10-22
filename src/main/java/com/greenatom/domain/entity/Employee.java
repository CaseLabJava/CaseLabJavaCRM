package com.greenatom.domain.entity;

import com.greenatom.domain.enums.JobPosition;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

/**
 * A Employee.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "firstname", length = 50)
    @NonNull
    private String name;

    @Column(name = "lastname", length = 50)
    @NonNull
    private String surname;

    @Column(name = "patronymic", length = 50)
    @NonNull
    private String patronymic;

    @Column(name = "job_position")
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @NonNull
    private String password;

    @OneToMany(mappedBy = "employee")
    private Set<Request> requests;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;
}
