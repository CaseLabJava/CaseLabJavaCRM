package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.Set;

/**
 * A Client.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "client_id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "firstname", length = 50)
    @NonNull
    private String name;

    @Column(name = "lastname", length = 50)
    @NonNull
    private String surname;

    @Column(name = "patronymic", length = 50)
    @NonNull
    private String patronymic;

    @Column(name = "bank_details")
    private String bank;

    @Column(name = "inn_and_kpp")
    private String inn;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "correspondent_account")
    private String correspondentAccount;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @OneToMany(mappedBy = "client")
    private Set<Request> requests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Client that = (Client) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
