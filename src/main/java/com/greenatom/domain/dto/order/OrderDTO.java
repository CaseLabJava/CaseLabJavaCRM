package com.greenatom.domain.dto.order;

import com.greenatom.domain.dto.ClientDTO;
import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.entity.Client;
import com.greenatom.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * A DTO for the Order.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private ClientDTO client;

    private EmployeeDTO employee;

    private String linkToFolder;

    private Date orderDate;

    private String orderStatus;
}
