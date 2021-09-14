package ru.javaops.topjava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava.UserTestData;
import ru.javaops.topjava.error.VoteDeadlineException;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.VoteRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava.RestaurantTestData.RESTAURANT3_ID;
import static ru.javaops.topjava.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;
    @Autowired
    protected VoteRepository repository;

    @Test
    void getByUserIdAndDate() {
        MATCHER.assertMatch(service.getByUserIdAndDate(UserTestData.USER_ID, VOTE_TEST_DATE), VOTE1);
    }

    @Test
    void getAll() {
        MATCHER.assertMatch(service.getAll(UserTestData.USER_ID), VOTE1, VOTE0);
    }

    @Test
    void update() {
        service.setClock(CLOCK_BEFORE_DEADLINE);
        service.save(UserTestData.USER_ID, RESTAURANT3_ID, VOTE_TEST_DATE);
        Vote v = service.getByUserIdAndDate(UserTestData.USER_ID, VOTE_TEST_DATE);
        MATCHER.assertMatch(service.getByUserIdAndDate(UserTestData.USER_ID, VOTE_TEST_DATE), getUpdated());
        Assertions.assertEquals(UserTestData.USER_ID, v.getUser().getId());
        Assertions.assertEquals(RESTAURANT3_ID, v.getRestaurant().getId());
    }

    @Test
    void updateAfterDeadline() {
        service.setClock(CLOCK_AFTER_DEADLINE);
        assertThrows(VoteDeadlineException.class, () ->
                service.save(UserTestData.USER_ID, RESTAURANT3_ID, VOTE_TEST_DATE));
    }

    @Test
    void create() {
        service.setClock(CLOCK_BEFORE_DEADLINE);
        Vote created = service.save(UserTestData.USER2_ID, RESTAURANT3_ID, VOTE_TEST_DATE);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        Vote v = repository.getById(newId);
        MATCHER.assertMatch(v, newVote);
        Assertions.assertEquals(UserTestData.USER2_ID, v.getUser().getId());
        Assertions.assertEquals(RESTAURANT3_ID, v.getRestaurant().getId());
    }

    @Test
    void createAfterDeadline() {
        service.setClock(CLOCK_AFTER_DEADLINE);
        Vote created = service.save(UserTestData.USER2_ID, RESTAURANT3_ID, VOTE_TEST_DATE);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        Vote v = repository.getById(newId);
        MATCHER.assertMatch(v, newVote);
        Assertions.assertEquals(UserTestData.USER2_ID, v.getUser().getId());
        Assertions.assertEquals(RESTAURANT3_ID, v.getRestaurant().getId());
    }
}