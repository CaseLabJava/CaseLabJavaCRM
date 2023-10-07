package com.greenatom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * A DTO for the Request.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private Long id;

    private Long clientId;

    private Long estimateId;

    private Long providerId;

    private Long supplyId;

    private Long employeeId;

    private String requestName;

    private Date requestDate;
}
