package ru.javaops.bootjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javaops.bootjava.HasId;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"created_at", "name", "restaurant_id"},
        name = "dish_unique_created_at_name_restaurant_id_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity implements HasId {
    @Column(name = "created_at", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(iso = DATE, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 0, max = 5000)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.createdAt = LocalDate.now();
        this.price = price;
    }
}
