package com.greenatom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the Client.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    private String name;

    private String director;

    private String bank;

    private String inn;

    private String ogrn;

    private String correspondentAccount;

    private String bik;

    private String address;

    private Long phoneNumber;
}
