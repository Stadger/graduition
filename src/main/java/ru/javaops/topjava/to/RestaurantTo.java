package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    int rating;

    public RestaurantTo(Integer id, String name, int rating) {
        super(id, name);
        this.rating = rating;
    }
}
