package com.greenatom.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

/**
 * SwaggerConfig класс используется для настройки Swagger, который является инструментом для описания и
 * документирования API. Этот класс использует аннотации @RequiredArgsConstructor и @Configuration.
 *
 * <p>Метод OpenAPI() создает новый объект OpenAPI, который является основным объектом Swagger. Он содержит информацию
 * о спецификации API, включая описание, версии, параметры безопасности и т.д. В данном случае, он добавляет новый
 * элемент безопасности “Bearer Authentication” и схему API ключа “Bearer Authentication”.
 *
 * <p>Метод createAPIKeyScheme() создает новую схему безопасности, которая представляет собой механизм для обеспечения
 * безопасности API. В этом случае, она устанавливает тип безопасности как HTTP и формат токена как JWT.
 * @author Даниил Змаев
 * @version 1.0
 */

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("CRM System for marketplace")
                        .version("1.0"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Выполните signIn или signUp, чтобы получить accessToken." +
                        " После получения accessToken, введите его в поле \"Value\".");
    }

    @Bean
    public OpenApiCustomizer sortTagsAlphabetically() {
        return openApi -> openApi.setTags(openApi.getTags()
                .stream()
                .sorted(Comparator.comparing(tag -> StringUtils.stripAccents(tag.getName())))
                .toList());
    }
}
