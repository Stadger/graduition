package ru.javaops.topjava.util;

import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.to.VoteTo;

import java.util.ArrayList;
import java.util.List;

public class VoteUtil {
    public static List<VoteTo> getTos(List<Vote> votes) {
        List<VoteTo> result = new ArrayList<>();
        for (Vote vote : votes) {
            result.add(new VoteTo(vote));
        }
        return result;
    }
}
