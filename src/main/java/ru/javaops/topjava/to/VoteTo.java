package ru.javaops.topjava.to;

import ru.javaops.topjava.model.Vote;

import java.time.LocalDate;

public class VoteTo extends BaseTo{
    private LocalDate created;
    private int restaurantId;
    private String restaurantName;
    private int userId;

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
