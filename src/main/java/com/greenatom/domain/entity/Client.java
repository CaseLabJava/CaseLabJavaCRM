package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * A Client.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "client_ID")
    private Long id;


    @Column(name = "company_name")
    @NonNull
    private String name;

    @Column(name = "name_of_director")
    @NonNull
    private String director;


    @Column(name = "bank")
    @NonNull
    private String bank;

    @Column(name = "inn_kpp")
    private String inn;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "correspondent_account")
    private String correspondentAccount;

    @Column(name = "bik")
    private String bik;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    @NonNull
    private Long phoneNumber;

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
