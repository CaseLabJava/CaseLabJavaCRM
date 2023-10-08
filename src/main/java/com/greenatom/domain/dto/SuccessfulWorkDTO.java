package com.greenatom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the Successful Work.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessfulWorkDTO {

    private Long id;

    private Long requestId;

    private String scopeOfWork;

    private String typeOfWork;

    private Long costPerUnit;
}
