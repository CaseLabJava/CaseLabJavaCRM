package com.greenatom.domain.dto.client;

import com.greenatom.domain.enums.ClientSource;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Информация о клиенте")
public class ClientResponseDTO {
    @Schema(description = "Id клиента", example = "1")
    private Long id;

    @Schema(description = "Имя клиента", example = "Иван")
    private String firstname;

    @Schema(description = "Фамилия клиента", example = "Иванов")
    private String lastname;

    @Schema(description = "Отчество клиента", example = "Иванович")
    private String patronymic;

    @Schema(description = "Название компании клиента", example = "АО Гринатом")
    private String company;

    @Schema(description = "название банка клиента", example = "СберБанк")
    private String bank;

    @Schema(description = "ИНН клиента", example = "1234567890123")
    private String inn;

    @Schema(description = "ОГРН клиента", example = "1097746819720")
    private String ogrn;

    @Schema(description = "Корреспондентский счет клиента", example = "12345678901234567890")
    private String correspondentAccount;

    @Schema(description = "Адресс клиента", example = "119017, город Москва, ул. Большая Ордынка, д.24 ")
    private String address;

    @Schema(description = "Электронная почта клиента", example = "pudge@mail.ru")
    private String email;

    @Schema(description = "Номер телефона клиента", example = "88005553535")
    private String phoneNumber;

    private Boolean legalEntity;

    private ClientSource clientSource;
}
