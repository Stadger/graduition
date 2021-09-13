package ru.javaops.topjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.RestaurantTestData.*;
import static ru.javaops.topjava.UserTestData.USER_MAIL;


class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @WithUserDetails(value = USER_MAIL)
    @Test
    void getWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(WITH_DISHES_MATCHER.contentJson(RESTAURANT1));
    }

    @WithUserDetails(value = USER_MAIL)
    @Test
    void getAllWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(RESTAURANT1, RESTAURANT2));
    }
}