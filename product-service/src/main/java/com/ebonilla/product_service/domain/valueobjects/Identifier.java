package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public final class Identifier {

    private final Integer id;

    private Identifier(Integer id) {
        this.id = id;
    }

    public static Optional<Identifier> create(Integer id, String reference, Notification notification){

        if (id != null && id <= 0) {
            notification.addError("Invalid number for " + reference);
            return Optional.empty();
        }

        return Optional.of(new Identifier(id));
    }
}
