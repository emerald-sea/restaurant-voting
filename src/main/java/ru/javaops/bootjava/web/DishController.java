package ru.javaops.bootjava.web;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.error.NotFoundException;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.DishRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javaops.bootjava.web.RestValidation.assureIdConsistent;
import static ru.javaops.bootjava.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    protected final Logger log = getLogger(getClass());

    public static final String REST_URL = "/api/restaurants/{restaurant_id}/dishes";

    @Autowired
    protected DishRepository dishRepository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.getExisted(id, restaurantId).orElseThrow(() ->
                new NotFoundException("Dish with id=" + id + " and restaurant=" + restaurantId + " not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable("restaurant_id") int restaurantId) {
        dishRepository.deleteExisted(id, restaurantId);
    }

    @SneakyThrows
    @GetMapping
    @Cacheable(value = "customers", key = "#id")
    public List<Dish> getAllByDate(@PathVariable("restaurant_id") int restaurantId,
                                   @RequestParam("date") @Nullable LocalDate localDate) {
        return dishRepository.findByRestaurantByDate(restaurantId, localDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish,
                                                   @PathVariable("restaurant_id") Integer restaurantId,
                                                   @RequestParam @Nullable LocalDate createdAt) {
        log.info("create {}", dish);
        checkNew(dish);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        dish.setRestaurant(restaurant.orElse(null));
        dish.setCreatedAt(createdAt);
        Dish created = dishRepository.prepareAndSave(dish, restaurantId);

        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(
                        new HashMap<String, Integer>() {{
                            put("id", created.getId());
                            put("restaurant_id", restaurantId);
                        }}
                ).toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id,
                       @PathVariable("restaurant_id") int restaurantId) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        dish.setRestaurant(restaurant.get());
        dishRepository.prepareAndSave(dish, restaurantId);
    }
}
