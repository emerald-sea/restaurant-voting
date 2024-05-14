package ru.javaops.bootjava.web.dish;

import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.web.MatcherFactory;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int DISH_1_ID = 1;
    public static final int DISH_2_ID = 2;
    public static final int DISH_3_ID = 3;
    public static final int DISH_NOT_FOUND = 100;
    public static final String DISH_1_NAME = "Dish 1";
    public static final String DISH_2_NAME = "Dish 2";
    public static final String DISH_3_NAME = "Dish 3";
    public static final int DISH_1_PRICE = 100;
    public static final int DISH_2_PRICE = 200;
    public static final int DISH_3_PRICE = 300;

    public static final Dish dish1 = new Dish(DISH_1_ID, DISH_1_NAME, DISH_1_PRICE);
    public static final Dish dish2 = new Dish(DISH_2_ID, DISH_2_NAME, DISH_2_PRICE);
    public static final Dish dish3 = new Dish(DISH_3_ID, DISH_3_NAME, DISH_3_PRICE);

    public static Dish getNew() {
        return new Dish(null, "New dish", 0);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1_ID, "Updated Dish", 111);
    }
}
