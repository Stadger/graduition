package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.User;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.VoteRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava.util.validation.ValidationUtil.checkTimeDeadline;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final UserService userService;
    private final RestaurantService restaurantService;

    public Vote getByUserIdAndDate(int userId, LocalDate date) {
        return repository.getByUserAndDate(date, userId).orElseThrow(() -> new EntityNotFoundException("vote with user id:" + userId + "and date not found" + date));
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Transactional
    public Vote save(int userId, int restaurantId, LocalDate date) {
        User user = userService.get(userId);
        Restaurant restaurant = restaurantService.get(restaurantId);
        Vote vote = repository.getByUserAndDate(date, userId).orElse(new Vote(null, date, user, restaurant));
        if (!vote.isNew()) {
            checkTimeDeadline(date);
        }
        vote.setRestaurant(restaurant);
        return repository.save(vote);
    }
}
