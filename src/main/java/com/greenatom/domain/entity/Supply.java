package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * A Supply.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "supply")
public class Supply {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "supply_id")
    private Long id;


    @Column(name = "delivery_price")
    @NonNull
    private String deliveryPrice;

    @Column(name = "material_name")
    @NonNull
    private String materialName;

    @Column(name = "provider_id")
    @NonNull
    private Long providerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supply supply = (Supply) o;
        return getId().equals(supply.getId()) && getDeliveryPrice().equals(supply.getDeliveryPrice()) && getMaterialName().equals(supply.getMaterialName()) && getProviderId().equals(supply.getProviderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDeliveryPrice(), getMaterialName(), getProviderId());
    }
}
