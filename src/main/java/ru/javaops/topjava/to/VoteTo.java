package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDate votedDate;
    int restaurantId;
    String restaurantName;

    public VoteTo(Integer id, LocalDate votedDate, int restaurantId, String restaurantName) {
        super(id);
        this.votedDate = votedDate;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
