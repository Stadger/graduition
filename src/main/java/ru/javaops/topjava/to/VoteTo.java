package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDate created;
    int restaurantId;
    String restaurantName;

    public VoteTo(Integer id, LocalDate created, int restaurantId, String restaurantName) {
        super(id);
        this.created = created;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
