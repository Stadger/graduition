package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    //https://stackoverflow.com/questions/38356565/filtering-using-entitygraph-in-spring-data-jpa-repository
    //return Restraint with notNull Dishes
    //if need all Restaurant add "  WHERE d is null OR" and property Dishes make Set
    @EntityGraph(attributePaths = { "dishes" })
    @Query("SELECT r FROM Restaurant r LEFT JOIN r.dishes d WHERE r.id = :restaurantId and d.created = :created")
    Optional<Restaurant> getWithDish(int restaurantId,LocalDate created);

    @EntityGraph(attributePaths = { "dishes" })
    @Query("SELECT r FROM Restaurant r LEFT OUTER JOIN r.dishes d WHERE d.created = :created ORDER BY r.name ASC ")
    List<Restaurant> getAllWithDish(LocalDate created);
}
