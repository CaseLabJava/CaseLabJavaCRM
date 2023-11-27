package com.greenatom.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * A Role
 */

@Entity
@Setter
@Getter
@Table(name = "role")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<Employee> employees;

}
