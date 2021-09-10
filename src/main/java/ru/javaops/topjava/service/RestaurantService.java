package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.repository.VoteRepository;
import ru.javaops.topjava.to.RestaurantTo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.javaops.topjava.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class RestaurantService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");
    private final RestaurantRepository repository;
    private final VoteRepository voteRepository;

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("restaurant with id:" + id + "not found"));
    }

    public Restaurant getWithDish(int id, LocalDate date) {
        return repository.getWithDish(id, date).orElseThrow(() -> new EntityNotFoundException("restaurant with id:" + id + "not found"));
    }

    @CacheEvict(value = "restaurantsWithDis", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithDis", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant rest = get(restaurant.id());
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    @Cacheable(value = "restaurantsWithDis", key = "#date")
    public List<Restaurant> getAllWithDish(LocalDate date) {
        return repository.getAllWithDish(date);
    }

    @Transactional
    public Optional<RestaurantTo> getTo(int id, LocalDate date) {
        return
                repository.findById(id).map(r ->
                        new RestaurantTo(id, r.getName(), voteRepository.countVoteByCreatedAndRestaurant(date, id)));
    }

    @Transactional
    public List<RestaurantTo> getAllTo(LocalDate date) {
        Map<Restaurant, Long> voteDay = voteRepository
                .findVoteByCreated(date)
                .stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));
        return
                getAll().stream()
                        .map(rest -> new RestaurantTo(rest.getId(), rest.getName(), voteDay.getOrDefault(rest, 0L).intValue()))
                        .sorted(Comparator.comparing(RestaurantTo::getRating))
                        .toList();
    }
}
