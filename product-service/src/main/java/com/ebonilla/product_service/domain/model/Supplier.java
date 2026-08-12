package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.domain.valueobjects.Email;
import com.ebonilla.product_service.domain.valueobjects.Identifier;
import com.ebonilla.product_service.domain.valueobjects.NumberPhone;
import com.ebonilla.product_service.domain.valueobjects.Text;
import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Supplier {

    private Integer id;
    private String supplierName;
    private String numberPhone;
    private String email;

    private Supplier(Integer id, String supplierName, String numberPhone,
                    String email) {
        this.id = id;
        this.supplierName = supplierName;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    public static Supplier create(Integer id, String supplierName, String numberPhone,
                                  String email, Notification notification){

        Optional<Identifier> validSupplierId = Identifier.create(id, "Supplier ID", notification);
        Optional<Text> validName = Text.create(supplierName, 100, "Supplier name", false, notification);
        Optional<NumberPhone> validNumberPhone = NumberPhone.create(numberPhone, "Supplier phone number", notification);
        Optional<Email> validEmail = Email.create(email, 100, "Supplier email", notification);

        if (notification.hasErrors()) {
            return null;
        }

        return new Supplier(
                validSupplierId.get().getId(),
                validName.get().getStr(),
                validNumberPhone.get().getNumberPhone(),
                validEmail.get().getEmail()
        );
    }
}
