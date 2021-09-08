package ru.javaops.topjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava.model.Dish;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.DishTestData.*;
import static ru.javaops.topjava.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava.RestaurantTestData.RESTAURANT2_ID;

class DishServiceTest extends AbstractServiceTest {
    @Autowired
    private DishService service;

    @Test
    void get() {
        Dish actual = service.get(DISH1_ID, RESTAURANT1_ID);
        MATCHER.assertMatch(actual, DISH1);
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.get(DISH1_ID, RESTAURANT2_ID));
    }

    @Test
    void delete() {
        service.delete(DISH1_ID, RESTAURANT1_ID);
        assertThrows(EntityNotFoundException.class, () -> service.get(DISH1_ID, RESTAURANT1_ID));
    }

    @Test
    void getAll() {
        MATCHER.assertMatch(service.getAll(RESTAURANT1_ID), DISH3, DISH2, DISH1);
    }

    @Test
    void getAllByDate() {
        MATCHER.assertMatch(service.getAllByDate(RESTAURANT1_ID, DISH_TEST_DATE), DISH3, DISH2, DISH1);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, RESTAURANT1_ID);
        MATCHER.assertMatch(service.get(DISH1_ID, RESTAURANT1_ID), getUpdated());
    }

    @Test
    void create() {
        Dish created = service.create(getNew(), RESTAURANT1_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        MATCHER.assertMatch(created, newDish);
        MATCHER.assertMatch(service.get(newId, RESTAURANT1_ID), newDish);
    }
}