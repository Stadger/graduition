package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.to.VoteTo;

import java.util.List;

@UtilityClass
public class VoteUtil {
    public static List<VoteTo> getTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::getTo).toList();
    }

    public static VoteTo getTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVotedDate(), vote.getRestaurant().getId(), vote.getRestaurant().getName());
    }
}
