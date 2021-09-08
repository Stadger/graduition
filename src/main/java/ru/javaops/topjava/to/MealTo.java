package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class MealTo extends BaseTo {

    LocalDateTime dateTime;

    String description;

    boolean excess;

    public MealTo(Integer id, LocalDateTime dateTime, String description, boolean excess) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.excess = excess;
    }
}
