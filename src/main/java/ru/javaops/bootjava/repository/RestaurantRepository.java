package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Transactional
    default Restaurant prepareAndSave(Restaurant restaurant) {
        restaurant.setName(restaurant.getName());
        return save(restaurant);
    }

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes  d WHERE d.createdAt=:createdAt")
    List<Restaurant> findAllByDate(LocalDate createdAt);
}
