package ru.javaops.topjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
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

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/profile/vote";
    private final VoteService service;
    private final VoteRepository repository;

    @PostMapping()
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        int userId = authUser.id();
        log.info("create vote {} for user {}", restaurantId, userId);
        Vote created = service.save(userId, restaurantId, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/all")
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return VoteUtil.getTos(service.getAll(authUser.id()));
    }

    @GetMapping()
    public ResponseEntity<VoteTo> get(@AuthenticationPrincipal AuthUser authUser,
                                      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get vote {} for user {}", date, authUser.id());
        if (date == null) date = LocalDate.now();
        Optional<VoteTo> to = repository.getByUserAndDate(date, authUser.id()).map(VoteUtil::getTo);
        return ResponseEntity.of(to);
    }
}
