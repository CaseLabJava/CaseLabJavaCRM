package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * A Request.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "request_id")
    private Long id;


    @Column(name = "client_id")
    @NonNull
    private Long clientId;

    @Column(name = "estimate_id")
    @NonNull
    private Long estimateId;

    @Column(name = "provider_id")
    @NonNull
    private Long providerId;

    @Column(name = "supply_id")
    @NonNull
    private Long supplyId;

    @Column(name = "employee_id")
    @NonNull
    private Long employeeId;

    @Column(name = "request_name")
    @NonNull
    private String requestName;

    @Column(name = "request_date")
    @NonNull
    private Date requestDate;
}
