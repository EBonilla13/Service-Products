package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.domain.valueobjects.ForeignId;
import com.ebonilla.product_service.domain.valueobjects.Identifier;
import com.ebonilla.product_service.domain.valueobjects.Price;
import lombok.*;

import java.math.BigDecimal;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductSupplier {

    private Integer id;
    private BigDecimal price;
    private Integer productId;
    private Integer supplierId;

    private ProductSupplier(Integer id, BigDecimal price, Integer productId, Integer supplierId) {
        this.id = id;
        this.price = price;
        this.productId = productId;
        this.supplierId = supplierId;
    }

    public static ProductSupplier create(Integer id, BigDecimal price, Integer productId,
                                                  Integer supplierId, Notification notification){
        Optional<Identifier> validId = Identifier.create(id, "Product_supplier ID", notification);
        Optional<Price> validPrice = Price.create(price, "Product price", notification);
        Optional<ForeignId> validProductId = ForeignId.create(productId, "Product ID", notification);
        Optional<ForeignId> validSupplierId = ForeignId.create(supplierId, "Supplier ID", notification);

        if (notification.hasErrors()) {
            return null;
        }

        return new ProductSupplier(
                validId.get().getId(),
                validPrice.get().getPrice(),
                validProductId.get().getId(),
                validSupplierId.get().getId()
        );
    }
}
