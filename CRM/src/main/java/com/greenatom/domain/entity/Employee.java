package com.greenatom.domain.entity;

import com.greenatom.domain.enums.JobPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

/**
 * An Employee
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
    @NotBlank
    @Size(min = 1, max = 50)
    private String firstname;

    @Column(name = "lastname", length = 50)
    @NotBlank
    @Size(min = 1, max = 50)
    private String surname;

    @Column(name = "patronymic", length = 50)
    @NotBlank
    @Size(min = 1, max = 50)
    private String patronymic;

    @Column(name = "address", length = 200)
    @Size(min = 1, max = 200)
    private String address;

    @Column(name = "job_position")
    @Enumerated(EnumType.STRING)
    @NonNull
    private JobPosition jobPosition;

    @Column(name = "salary")
    @Min(0)
    private Long salary;

    @Column(name = "email")
    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Column(name = "phone_number")
    @NotBlank
    @Size(min = 1, max = 20)
    private String phoneNumber;

    @Column(name = "username")
    @NotBlank
    private String username;

    @Column(name = "password")
    @NotBlank
    @Size(min = 60, max = 60)
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
