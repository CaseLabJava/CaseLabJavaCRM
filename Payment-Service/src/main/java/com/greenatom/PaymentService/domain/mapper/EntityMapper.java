package com.greenatom.paymentservice.domain.mapper;

import com.greenatom.paymentservice.domain.entity.Card;
import com.greenatom.paymentservice.domain.entity.Payment;
import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import org.mapstruct.*;

import java.time.Instant;
import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);

}

