package ru.javaops.topjava.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava.util.DateTimeUtil.checkDate;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";
    private final RestaurantService service;
    private final RestaurantRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id,
                                                    @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        date = checkDate(date);
        log.info("get Restaurant {} with dish and date{}", id, date);
        return ResponseEntity.of(repository.getWithDish(id, date));
    }

    @GetMapping()
    public List<Restaurant> getAllWithDishes(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        date = checkDate(date);
        log.info("get Restaurant with dish for date{}", date);
        return service.getAllWithDish(date);
    }
}
