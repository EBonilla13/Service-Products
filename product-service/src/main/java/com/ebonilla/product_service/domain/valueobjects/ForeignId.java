package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class ForeignId {

    private final Integer id;

    private ForeignId(Integer id){
        this.id = id;
    }

    public static Optional<ForeignId> create(Integer id, String reference, Notification notification){

        if (id == null){
            notification.addError(reference + " cannot be null");
            return Optional.empty();
        }else if (id <= 0){
            notification.addError("Invalid number for " + reference);
            return Optional.empty();
        }

        return Optional.of(new ForeignId(id));
    }
}
