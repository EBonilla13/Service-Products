package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.domain.valueobjects.Identifier;
import com.ebonilla.product_service.domain.valueobjects.Text;
import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Measurement {

    private Integer id; // Can be a positive number (update) or null (create)
    private String unit;
    private String symbol;

    private Measurement(Integer id, String unit, String symbol) {
        this.id = id;
        this.unit = unit;
        this.symbol = symbol;
    }

    public static Measurement create(Integer id, String unit, String symbol, Notification notification){

        Optional<Identifier> validId = Identifier.create(id, "Measurement ID", notification);
        Optional<Text> validUnit = Text.create(unit, 50, "Measurement unit", false, notification);
        Optional<Text> validSymbol = Text.create(symbol, 10, "Measurement unit", false, notification);

        if (notification.hasErrors()) {
            return null;
        }

        return new Measurement(
                validId.get().getId(),
                validUnit.get().getStr(),
                validSymbol.get().getStr()
        );
    }
}
