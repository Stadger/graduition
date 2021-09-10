package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.topjava.HasId;
import ru.javaops.topjava.model.Restaurant;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo implements Comparable<RestaurantTo> {
    private int rating;

    public RestaurantTo(Integer id, String name, int rating) {
        super(id, name);
        this.rating = rating;
    }

    @Override
    public int compareTo(RestaurantTo o) {
        int res = this.rating - o.rating;
        if (res != 0) {
            return res;
        }
        return this.name.compareTo(o.name);
    }
}
