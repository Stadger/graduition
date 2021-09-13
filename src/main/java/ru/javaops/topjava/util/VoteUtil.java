package ru.javaops.topjava.util;

import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.to.VoteTo;

import java.util.List;

public class VoteUtil {
    public static List<VoteTo> getTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::getTo).toList();
    }

    public static VoteTo getTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getCreated(), vote.getRestaurant().getId(), vote.getRestaurant().getName());
    }
}
