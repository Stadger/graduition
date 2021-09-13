package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.User;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.repository.UserRepository;
import ru.javaops.topjava.repository.VoteRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava.util.validation.ValidationUtil.checkOptional;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkTimeDeadline;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Vote getByUserIdAndDate(int userId, LocalDate date) {
        return repository.getByUserAndDate(date, userId).orElse(null);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Transactional
    public Vote save(int userId, int restaurantId, LocalDate date) {
        User user = userRepository.getById(userId);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Vote vote = repository.getByUserAndDate(date, userId).orElse(new Vote(null, date, user, restaurant));
        if (!vote.isNew()) {
            checkTimeDeadline(date);
        }
        vote.setRestaurant(restaurant);
        return repository.save(vote);
    }
}
