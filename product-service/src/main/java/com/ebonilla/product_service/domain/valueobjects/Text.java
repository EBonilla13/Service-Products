package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public final class Text {

    private final String str;

    private Text(String str){
        this.str = str;
    }

    public static Optional<Text> create(String str, Integer maxLength, String reference,
                                        Boolean nullable, Notification notification){
        if (!nullable && str == null){
            notification.addError(reference + " cannot be null");
            return Optional.empty();
        }
        if (str != null && (str.isEmpty() || str.length() > maxLength)){
            notification.addError("Invalid length for " + reference);
            return Optional.empty();
        }
        return Optional.of(new Text(str));
    }

}
