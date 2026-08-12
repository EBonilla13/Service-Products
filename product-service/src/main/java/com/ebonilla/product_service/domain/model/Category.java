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
public class Category {

    private Integer id;
    private String categoryName;

    private Category(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    // Method to apply business logic to attributes entity,
    // it can return a new category or a notification with errors messages
    public static Category create(Integer id, String categoryName, Notification notification){

        Optional<Identifier> identifier = Identifier.create(id, "Category ID", notification);
        Optional<Text> text = Text.create(categoryName, 100, "Category Name", false, notification);

        return !notification.hasErrors()
                ? new Category(identifier.get().getId(), text.get().getStr())
                : null;
    }
}