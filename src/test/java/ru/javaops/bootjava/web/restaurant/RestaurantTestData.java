package ru.javaops.bootjava.web.restaurant;

import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT_1_ID = 1;
    public static final int RESTAURANT_2_ID = 2;
    public static final int RESTAURANT_3_ID = 3;
    public static final int RESTAURANT_NOT_FOUND = 100;
    public static final String RESTAURANT_1_NAME = "Restaurant 1";
    public static final String RESTAURANT_2_NAME = "Restaurant 2";
    public static final String RESTAURANT_3_NAME = "Restaurant 3";

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_1_ID, RESTAURANT_1_NAME);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_2_ID, RESTAURANT_2_NAME);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_3_ID, RESTAURANT_3_NAME);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_1_ID, "Updated Restaurant");
    }
}
