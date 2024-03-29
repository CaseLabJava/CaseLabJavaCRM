package com.greenatom.domain.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

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

    @Schema(description = "Адрес клиента", example = "119017, город Москва, ул. " +
            "Большая Ордынка, д.24 ")
    private String address;

    @Schema(description = "Электронная почта клиента", example = "pudge@mail.ru")
    private String email;

    @Schema(description = "Номер телефона клиента", example = "88005553535")
    private String phoneNumber;
}
