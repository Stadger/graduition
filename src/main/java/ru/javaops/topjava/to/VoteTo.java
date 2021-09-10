package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.topjava.model.Vote;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDate created;
    int restaurantId;
    String restaurantName;
    int userId;

    public VoteTo(Integer id, LocalDate created, int restaurantId, String restaurantName, int userId) {
        super(id);
        this.created = created;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.userId = userId;
    }

    public VoteTo(Vote vote) {
        super(vote.getId());
        this.created = vote.getCreated();
        this.restaurantId = vote.getRestaurant().getId();
        this.restaurantName = vote.getRestaurant().getName();
        this.userId = vote.getUser().getId();
    }
}
