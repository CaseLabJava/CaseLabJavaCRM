package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

/**
 * A Request.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "request_id")
    private Long id;

    @Column(name = "link_to_folder")
    private String linkToFolder;

    @Column(name = "date_time")
    private Date requestDate;

    @Column(name = "status")
    private String requestStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "request")
    private List<CartProduct> cartProducts;
}
