package ru.javaops.topjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava.DishTestData;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.Restaurant;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.DishTestData.DISHES_REST1_TODAY;
import static ru.javaops.topjava.RestaurantTestData.*;
import static ru.javaops.topjava.UserTestData.NOT_FOUND;


class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService service;

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT1_ID);
        MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getWithDish() {
        Restaurant restaurant = service.getWithDish(RESTAURANT1_ID, TEST_DATE);
        MATCHER.assertMatch(restaurant, RESTAURANT1);
        DishTestData.MATCHER.assertMatch(restaurant.getDishes(), DISHES_REST1_TODAY);
        RESTAURANT1.setDishes(DISHES_REST1_TODAY);
        WITH_DISHES_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT1_ID);
        assertThrows(EntityNotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        MATCHER.assertMatch(service.get(RESTAURANT1_ID), getUpdated());
    }

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void getAll() {
        MATCHER.assertMatch(service.getAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }

    @Test
    void getAllWithDish() {
        List<Restaurant> restaurants = service.getAllWithDish(TEST_DATE);
        MATCHER.assertMatch(restaurants, RESTAURANT1, RESTAURANT2);
    }
}