package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.domain.valueobjects.ForeignId;
import com.ebonilla.product_service.domain.valueobjects.Identifier;
import com.ebonilla.product_service.domain.valueobjects.Text;
import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Product {

    private Integer id;
    private String productName;
    private String productModel;
    private String productSpecification;
    private Integer categoryId;
    private Integer measurementId;

    private Product(Integer id, String productName, String productModel, String productSpecification,
                    Integer categoryId, Integer measurementId) {
        this.id = id;
        this.productName = productName;
        this.productModel = productModel;
        this.productSpecification = productSpecification;
        this.categoryId = categoryId;
        this.measurementId = measurementId;
    }

    public static Product create(Integer id, String productName, String productModel, String productSpecification,
                                 Integer categoryId, Integer measurementId, Notification notification){
        // Aplicamos reglas de negocio a los atributos de la entidad
        Optional<Identifier> productId = Identifier.create(id, "product ID", notification);
        Optional<Text> validName = Text.create(productName, 100, "product name", false, notification);
        Optional<Text> validModel = Text.create(productModel, 100, "product model", false, notification);
        Optional<Text> validSpec = Text.create(productSpecification, 100, "product specification", true, notification);
        Optional<ForeignId> validCategoryId = ForeignId.create(categoryId, "category ID", notification);
        Optional<ForeignId> validMeasurementId = ForeignId.create(measurementId, "measurement ID", notification);

        // Validamos si se presentaron errores en las entidades, en caso de no haber errores se crea y devuelve
        // una instancia de la entidad.
        if (notification.hasErrors()){
            return null;
        }

        return new Product(
                productId.get().getId(),
                validName.get().getStr(),
                validModel.get().getStr(),
                validSpec.get().getStr(),
                validCategoryId.get().getId(),
                validMeasurementId.get().getId()
        );
    }
}
