package ru.javaops.topjava;

import ru.javaops.topjava.model.Dish;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static ru.javaops.topjava.RestaurantTestData.RESTAURANT1;
import static ru.javaops.topjava.RestaurantTestData.RESTAURANT2;

public class DishTestData {
    public static final LocalDate DISH_TEST_DATE = LocalDate.now();
    public static final MatcherFactory.Matcher<Dish> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 3;

    public static final Dish DISH1 = new Dish(DISH1_ID, "soup FirstRest", RESTAURANT1, 500);
    public static final Dish DISH2 = new Dish(DISH2_ID, "pasta FirstRest", RESTAURANT1, 300);
    public static final Dish DISH3 = new Dish(DISH3_ID, "coffe FirstRest", RESTAURANT1, 200);
    public static final Dish DISH4 = new Dish(DISH4_ID, "soup SecondRest", RESTAURANT2, 500);

    public static final List<Dish> DISHES_REST1_TODAY = List.of(DISH3, DISH2, DISH1);
    public static final List<Dish> DISHES_REST2_TODAY = List.of(DISH4);
    public static final List<Dish> DISHES_REST3_TODAY = Collections.emptyList();

    public static Dish getNew() {
        return new Dish(null, "NewDish", RESTAURANT1, 1000);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "UpdatedName", RESTAURANT1, 700);
    }
}
