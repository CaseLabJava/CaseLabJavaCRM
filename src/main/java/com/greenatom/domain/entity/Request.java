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

    @Column(name = "employee_id")
    @NonNull
    private Long employeeId;

    @Column(name = "link_to_folder")
    @NonNull
    private String linkToFolder;

    @Column(name = "date_time")
    @NonNull
    private Date requestDate;

    @Column(name = "status")
    @NonNull
    private String requestStatus;
}
