package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public final class Price {

    private final BigDecimal price;
    public static final BigDecimal MIN_PRICE = new BigDecimal("0.0");

    private Price(BigDecimal price) {
        this.price = price;
    }

    public static Optional<Price> create(BigDecimal price, String reference, Notification notification){

        if (price == null){
            notification.addError(reference + " cannot be null");
            return Optional.empty();
        }
        if (price.compareTo(MIN_PRICE) < 0) {
            notification.addError(reference + " cannot be less than zero");
            return Optional.empty();
        }

        return Optional.of(new Price(price));
    }
}
