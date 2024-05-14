package ru.javaops.bootjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.bootjava.HasId;
import ru.javaops.bootjava.model.Dish;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends NamedTo implements HasId {
    List<Dish> dishes;


    public MenuTo(Integer id, String name, List<Dish> dishes) {
        super(id, name);
        this.dishes = dishes;
    }


    @Override
    public String toString() {
        return "MenuTo:" + id + '[' + name + "] Dishes: " + dishes.toString();
    }
}
