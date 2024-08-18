package ru.javaops.bootjava.web.dish;

import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.web.MatcherFactory;

import java.time.LocalDate;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int DISH_1_ID = 1;
    public static final int DISH_NOT_FOUND = 100;
    public static final String DISH_1_NAME = "Dish 1";
    public static final int DISH_1_PRICE = 100;
    public static final Dish dish1 = new Dish(DISH_1_ID, DISH_1_NAME, DISH_1_PRICE, LocalDate.now());

    public static Dish getNew() {
        return new Dish(null, "New dish", 0, LocalDate.now());
    }

    public static Dish getNewForTomorrow() {
        return new Dish(null, "New dish for tomorrow", 0, LocalDate.now().plusDays(1));
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1_ID, "Updated Dish", 111, LocalDate.now());
    }
}
