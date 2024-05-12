package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurant AND d.createdAt=:createdAt")
    List<Dish> findByRestaurantByDate(int restaurant, LocalDate createdAt);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurant")
    Dish getExisted(int id, int restaurant);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurant")
    int deleteExisted(int id, int restaurant);

    @Transactional
    default Dish prepareAndSave(Dish dish, int restaurantId) {
        dish.setName(dish.getName().toLowerCase());
        dish.setPrice(dish.getPrice());
        return save(dish);
    }
}
