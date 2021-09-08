package ru.javaops.topjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.VoteRepository;
import ru.javaops.topjava.service.VoteService;
import ru.javaops.topjava.to.VoteTo;
import ru.javaops.topjava.util.VoteUtil;
import ru.javaops.topjava.web.AuthUser;
import ru.javaops.topjava.web.meal.MealController;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/profile/vote";
    private final VoteService service;

    @PostMapping()
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        int userId = authUser.id();
        log.info("create vote {} for user {}", restaurantId, userId);
        Vote created = service.save(userId,restaurantId, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/MyVotes")
    public List<VoteTo> getByUser(@AuthenticationPrincipal AuthUser authUser) {
        List<Vote> votes = service.getAll(authUser.id());
        votes.stream().forEach(System.out::println);
        return VoteUtil.getTos(votes);
    }
}
