package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javaops.topjava.model.Dish;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.repository.DishRepository;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava.util.validation.ValidationUtil.checkNotFoundWithId;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkOptional;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public Dish get(int id, int restId) {
        return checkOptional(dishRepository.get(id, restId), id);
    }

    @CacheEvict(value = "restaurantsWithDish", allEntries = true)
    public void delete(int id, int restId) {
        checkNotFoundWithId(dishRepository.delete(id, restId), id);
    }

    public List<Dish> getAll(int restId) {
        return dishRepository.getByRestaurant(restId);
    }

    public List<Dish> getAllByDate(int restId, LocalDate date) {
        return dishRepository.getByRestaurantAndDate(date, restId);
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithDish", allEntries = true)
    public void update(Dish dish, int restId) {
        Assert.notNull(dish, "dish must not be null");
        get(dish.id(), restId);
        dish.setRestaurant(restaurantRepository.findById(restId).get());
        checkNotFoundWithId(dishRepository.save(dish), dish.id());
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithDish", allEntries = true)
    public Dish create(Dish dish, int restId) {
        Assert.notNull(dish, "dish must not be null");
        ValidationUtil.checkNew(dish);
        Restaurant restaurant = checkOptional(restaurantRepository.findById(restId), restId);
        dish.setRestaurant(restaurant);
        return dishRepository.save(dish);
    }
}
