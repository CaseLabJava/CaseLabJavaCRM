package com.greenatom.domain.dto.client;

import com.greenatom.domain.enums.ClientSource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegistrationDTO {
    @Schema(description = "Имя клиента", example = "Иван")
    @NotNull
    @Size(min = 1, max = 20)
    private String firstname;

    @Schema(description = "Пароль для аутентификации", example = "dobryachok23")
    private String password;

    @Schema(description = "Фамилия клиента", example = "Иванов")
    private String lastname;

    @Schema(description = "Отчество клиента", example = "Иванович")
    private String patronymic;

    @Schema(description = "Название компании клиента", example = "АО Гринатом")
    private String company;

    @Schema(description = "название банка клиента", example = "СберБанк")
    private String bank;

    @Schema(description = "ИНН клиента", example = "7706729736")
    private String inn;

    @Schema(description = "ОГРН клиента", example = "1097746819720")
    private String ogrn;

    @Schema(description = "Корреспондентский счет клиента", example = "123456789")
    private String correspondentAccount;

    @Schema(description = "Адрес клиента", example = "119017, город Москва, ул. " +
            "Большая Ордынка, д.24 ")
    private String address;

    @Schema(description = "Электронная почта клиента", example = "pudge@mail.ru")
    @Email
    @NotNull
    private String email;

    @Schema(description = "Номер телефона клиента", example = "88005553535")
    @Size(min = 5, max = 15)
    private String phoneNumber;

    @Schema(description = "Флаг показывающий, является ли клиент юр лицом или нет", example = "true")
    @NotNull
    private Boolean legalEntity;

    @Schema(description = "флаг,указывающий откуда пришел клиент", example = "SELF_SERVICE")
    private ClientSource clientSource;
}
