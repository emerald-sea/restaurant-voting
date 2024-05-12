package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface  VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restaurant AND v.createdAt=:createdAt")
    Optional<Vote> getExistedToday(int id, int restaurant, LocalDate createdAt);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restaurant")
    int deleteExisted(int id, int restaurant);



}
