package com.greenatom.domain.entity;

import com.greenatom.domain.enums.JobPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * An Employee.
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "firstname", length = 50)
    @NonNull
    private String firstname;

    @Column(name = "lastname", length = 50)
    @NonNull
    private String surname;

    @Column(name = "patronymic", length = 50)
    @NonNull
    private String patronymic;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "job_position")
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    @Column(name = "salary")
    private Long salary;

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
    private Set<Order> orders;

    @OneToMany(mappedBy = "employee")
    private Set<Claim> claims;

    @OneToMany(mappedBy = "employee")
    private Set<PreparingOrder> preparingOrders;

    @OneToOne(mappedBy = "employee")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    public String getFullName() {
        return String.format("%s %s %s", surname, firstname, patronymic);
    }

}
