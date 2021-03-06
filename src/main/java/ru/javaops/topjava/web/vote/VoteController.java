package ru.javaops.topjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.VoteRepository;
import ru.javaops.topjava.service.VoteService;
import ru.javaops.topjava.to.VoteTo;
import ru.javaops.topjava.util.VoteUtil;
import ru.javaops.topjava.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava.util.DateTimeUtil.checkDate;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/profile/votes";
    private final VoteService service;
    private final VoteRepository repository;

    @PostMapping
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        int userId = authUser.id();
        log.info("create vote {} for user {}", restaurantId, userId);
        Vote created = service.save(userId, restaurantId, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        int userId = authUser.id();
        log.info("update vote {} for user {}", restaurantId, userId);
        service.update(id, userId, restaurantId, LocalDate.now());
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all votes for user {}", authUser.id());
        return VoteUtil.getTos(service.getAll(authUser.id()));
    }

    @GetMapping("/date")
    public ResponseEntity<VoteTo> get(@AuthenticationPrincipal AuthUser authUser,
                                      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        date = checkDate(date);
        log.info("get vote {} for user {}", date, authUser.id());
        Optional<VoteTo> to = repository.getByUserAndDate(date, authUser.id()).map(VoteUtil::getTo);
        return ResponseEntity.of(to);
    }
}
