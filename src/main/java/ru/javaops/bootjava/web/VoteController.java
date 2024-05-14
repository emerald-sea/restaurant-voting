package ru.javaops.bootjava.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.error.NotFoundException;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.model.User;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.repository.VoteRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javaops.bootjava.util.VoteUtil.checkTime;
import static ru.javaops.bootjava.util.VoteUtil.checkUser;
import static ru.javaops.bootjava.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    protected final Logger log = getLogger(getClass());

    static final String REST_URL = "/api/restaurants/{restaurant_id}/votes";

    static final CharSequence VOTE_BEFORE = "11:00:00";

    @Autowired
    protected VoteRepository repository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id, @PathVariable("restaurant_id") int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Vote vote = repository.getExistedToday(id, restaurantId, LocalDate.now()).orElseThrow(() ->
                new NotFoundException("Vote with id=" + id + " and restaurant=" + restaurantId + " are not found"));
        checkUser(authUser, vote, "You are not allowed to see this vote");
        return vote;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable("restaurant_id") int restaurantId,
                       @AuthenticationPrincipal AuthUser authUser) {
        Vote vote = repository.getExistedToday(id, restaurantId, LocalDate.now()).orElseThrow(() ->
                new NotFoundException("Vote with id=" + id + " and restaurant=" + restaurantId + " are not found"));
        checkUser(authUser, vote, "You are not allowed to delete this vote");
        checkTime(VOTE_BEFORE);
        repository.deleteExisted(id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody Vote vote,
                                                   @PathVariable("restaurant_id") Integer restaurantId,
                                                   @AuthenticationPrincipal AuthUser authUser) {
        log.info("create {}", vote);
        checkNew(vote);
        checkTime(VOTE_BEFORE);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        vote.setRestaurant(restaurant.get());
        User user = authUser.getUser();
        vote.setUser(user);
        Vote created = repository.save(vote);

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
}
