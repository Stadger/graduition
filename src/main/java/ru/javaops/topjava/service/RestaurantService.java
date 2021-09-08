package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("restaurant with id:" + id + "not found"));
    }

    public Restaurant getWithDish(int id, LocalDate date) {
        return repository.getWithDish(id, date).orElseThrow(() -> new EntityNotFoundException("restaurant with id:" + id + "not found"));
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant rest = get(restaurant.id());
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public List<Restaurant> getAllWithDish(LocalDate date) {
        return repository.getAllWithDish(date);
    }


}
