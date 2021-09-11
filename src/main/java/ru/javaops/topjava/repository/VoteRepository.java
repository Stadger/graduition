package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.created = :created and v.user.id = :userId")
    Optional<Vote> getByUserAndDate(LocalDate created, int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.created DESC")
    List<Vote> getAll(int userId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.created = :created and v.restaurant.id = :restaurantId")
    int countVoteByCreatedAndRestaurant(LocalDate created, int restaurantId);

    @Query("SELECT v FROM Vote v WHERE  v.created = :created ORDER BY v.restaurant.id ASC")
    List<Vote> findVoteByCreated(LocalDate created);
}
