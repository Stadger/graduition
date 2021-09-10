package ru.javaops.topjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava.UserTestData;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.VoteRepository;

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
        service.save(UserTestData.USER_ID, RESTAURANT3_ID, VOTE_TEST_DATE);
        WITH_USER_AND_REST_MATCHER.assertMatch(service.getByUserIdAndDate(UserTestData.USER_ID, VOTE_TEST_DATE), getUpdated());
    }

    @Test
    void create() {
        Vote created = service.save(UserTestData.USER_ID, RESTAURANT1_ID, VOTE_TEST_DATE);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        WITH_USER_AND_REST_MATCHER.assertMatch(repository.getById(newId), newVote);
    }
}