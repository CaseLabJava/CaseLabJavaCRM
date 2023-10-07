package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * A Successful Work.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "successful_work")
public class SuccessfulWork {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "work_id")
    private Long id;

    @Column(name = "request_id")
    @NonNull
    private Long requestId;

    @Column(name = "scope_of_work")
    @NonNull
    private String scopeOfWork;

    @Column(name = "type_of_work")
    @NonNull
    private String typeOfWork;

    @Column(name = "cost_per_unit")
    @NonNull
    private Long costPerUnit;
}
