package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository  extends BaseRepository<Vote>{

    @Query("SELECT v FROM Vote v WHERE v.created = :created and v.user.id = :userId")
    Optional<Vote> getByUserAndDate(LocalDate created, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.created DESC")
    List<Vote> getAll(int userId);
}
