package ru.javaops.topjava;

import ru.javaops.topjava.model.Restaurant;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.topjava.DishTestData.*;

public class RestaurantTestData {
    public static final LocalDate TEST_DATE = LocalDate.now();
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static MatcherFactory.Matcher<Restaurant> WITH_DISHES_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("dishes.restaurant").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "FirstRest");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "SecondRest");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID, "ThirdRest");

    static {
        RESTAURANT1.setDishes(DISHES_REST1_TODAY);
        RESTAURANT2.setDishes(DISHES_REST2_TODAY);
        RESTAURANT3.setDishes(DISHES_REST3_TODAY);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRest");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName");
    }
}
