package ru.javaops.topjava;

import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.to.VoteTo;
import ru.javaops.topjava.util.VoteUtil;

import java.time.Clock;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.topjava.RestaurantTestData.*;
import static ru.javaops.topjava.util.DateTimeUtil.DEADLINE;
import static ru.javaops.topjava.util.DateTimeUtil.createClock;

public class VoteTestData {
    public static final LocalDate VOTE_TEST_DATE = LocalDate.now();
    public static final LocalDate VOTE_TEST_DATE2 = LocalDate.of(2021, 8, 25);
    public static final Clock CLOCK_BEFORE_DEADLINE = createClock(VOTE_TEST_DATE, DEADLINE.minusHours(1));
    public static final Clock CLOCK_AFTER_DEADLINE = createClock(VOTE_TEST_DATE, DEADLINE.plusHours(1));
    public static final MatcherFactory.Matcher<Vote> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");
    public static final MatcherFactory.Matcher<VoteTo> MATCHER_TO = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);
    public static MatcherFactory.Matcher<Vote> WITH_USER_AND_REST_MATCHER =
            MatcherFactory.usingAssertions(Vote.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("user.password", "user.registered", "restaurant.dishes").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int VOTE1_ID = 4;
    public static final int VOTE2_ID = 5;

    public static final Vote VOTE0 = new Vote(1, VOTE_TEST_DATE2, UserTestData.user, RESTAURANT1);
    public static final Vote VOTE1 = new Vote(VOTE1_ID, UserTestData.user, RESTAURANT2);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, UserTestData.admin, RESTAURANT3);
    public static final VoteTo VOTE1_TO = VoteUtil.getTo(VOTE1);


    public static Vote getNew() {
        return new Vote(null, VOTE_TEST_DATE, UserTestData.user2, RESTAURANT3);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, UserTestData.user, RESTAURANT3);
    }
}
